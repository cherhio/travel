package cherhio.dao;

import cherhio.domain.RouteImg;

import java.util.List;

/**
 * @author cherhio
 * @date Created in 2019-04-24 23:50
 */
public interface RouteImgDao {
    /**
     * 根据route的id查询图片
     * @param rid
     * @return
     */
    public List<RouteImg> findByRid(int rid);
}
