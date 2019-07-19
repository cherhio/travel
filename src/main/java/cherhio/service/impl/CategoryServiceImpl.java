package cherhio.service.impl;

import cherhio.dao.CategoryDao;
import cherhio.dao.impl.CategoryDaoImpl;
import cherhio.domain.Category;
import cherhio.service.CategoryService;
import cherhio.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author cherhio
 * @date Created in 2019-04-23 17:12
 */
public class CategoryServiceImpl implements CategoryService {
    CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        //1.从redis中查询
        //1.获取jedis的客户端
        Jedis jedis = JedisUtil.getJedis();
//        Set<String> category = jedis.zrange("category", 0, -1);
        //改写方法，查询sortedset中的分数(cid)和值
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);


        List<Category> cs = null;
        //2.判断查询的集合是否为空
        if (category == null | category.size() == 0){
//            System.out.println("从数据库查询...");
            //如果为空，是第一次访问，需要从数据库查询，存入redis
            cs = dao.findAll();
            //存储到redis中的的一个叫category的key
            for (int i = 0; i < cs.size(); i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }
        }else {
//            System.out.println("从redis中查询。。。");
            //如果不为空,把set数据存入list
            cs = new ArrayList<Category>();
            for (Tuple tuple : category) {
                Category categorys = new Category();
                categorys.setCname(tuple.getElement());
                categorys.setCid((int)tuple.getScore());
                cs.add(categorys);
            }
        }


        //如果不为空，直接返回
        

        return cs;
    }
}
