package cherhio.dao;

import cherhio.domain.Route;

import java.util.List;

/**
 * @author cherhio
 * @date Created in 2019-04-23 23:22
 */
public interface RouteDao {

    List<Route> findByPage(int cid, int start, int pageSize,String rname);

    int findTotalCount(int cid,String rname);

    public Route findOne(int rid);
}
