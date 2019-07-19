package cherhio.dao.impl;

import cherhio.dao.RouteDao;
import cherhio.domain.Route;
import cherhio.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cherhio
 * @date Created in 2019-04-23 23:26
 */
public class RouteDaoImpl implements RouteDao {
   private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {
       // String sql = "select * from tab_route where cid = ? limit ? , ?";
        String sql = "select * from tab_route where 1 = 1 ";

        StringBuilder sb = new StringBuilder(sql);

        //创建一个集合放条件
        List params = new ArrayList();
        //判断参数是否有值
        if (cid != 0 ){
            sb.append(" and cid  = ? ");
            params.add(cid);
        }

        if (rname != null && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }

        //分页条件
        sb.append(" limit ? , ? ");


        sql = sb.toString();

        params.add(start);
        params.add(pageSize);


        return template.query(sql,new BeanPropertyRowMapper<>(Route.class),params.toArray());
    }

    @Override
    public int findTotalCount(int cid,String rname) {

//        String sql = "select count(*) from tab_route where cid = ?";
        //为了通用性，定义一个模板，需要什么，再添加
        String sql = "select count(*) from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);

        //创建一个集合放条件
        List params = new ArrayList();
        //判断参数是否有值
        if (cid != 0 ){
            sb.append(" and cid  = ? ");
            params.add(cid);
        }

        if (rname != null && rname.length()>0){
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }

        sql = sb.toString();

        return template.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select * from tab_route where rid = ?";

        return template.queryForObject(sql,new BeanPropertyRowMapper<>(Route.class),rid);
    }
}
