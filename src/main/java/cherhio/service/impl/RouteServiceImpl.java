package cherhio.service.impl;

import cherhio.dao.FavoriteDao;
import cherhio.dao.RouteDao;
import cherhio.dao.RouteImgDao;
import cherhio.dao.SellerDao;
import cherhio.dao.impl.FavoriteDaoImpl;
import cherhio.dao.impl.RouteDaoImpl;
import cherhio.dao.impl.RouteImgDaoImpl;
import cherhio.dao.impl.SellerDaoImpl;
import cherhio.domain.PageBean;
import cherhio.domain.Route;
import cherhio.domain.RouteImg;
import cherhio.domain.Seller;
import cherhio.service.RouteService;

import java.util.List;

/**
 * @author cherhio
 * @date Created in 2019-04-23 23:25
 */
public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname) {
        //封装pageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);

        //设置每页显示的条数
        pb.setPageSize(pageSize);

        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;

        List<Route> list = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setList(list);

        //设置总页数 = 总记录数/每页显示条数
        int totalPage = totalCount % pageSize == 0 ? (totalCount/pageSize) : (totalCount/pageSize) + 1;
        pb.setTotalPage(totalPage);

        return pb;
    }

    @Override
    public Route findOne(String rid) {
        //1.根据id去route表中查询对象
        Route route = routeDao.findOne(Integer.parseInt(rid));

        //2.根据route的Id查询图片的集合信息
        List<RouteImg> list = routeImgDao.findByRid(route.getRid());

        route.setRouteImgList(list);

        //3.根据route的sid查询卖家对象
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);

        //4.查询收藏次数
        int count = favoriteDao.findCountByRid(route.getRid());
        route.setCount(count);
        return route;
    }


}
