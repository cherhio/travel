package cherhio.service;

import cherhio.domain.PageBean;
import cherhio.domain.Route;

/**
 * @author cherhio
 * @date Created in 2019-04-23 23:25
 */
public interface RouteService {
    public PageBean<Route> pageQuery(int cid,int currentPage,int pageSize,String rname);

    /**
     * 根据id查询
     * @param rid
     * @return
     */
    public Route findOne(String rid);

}
