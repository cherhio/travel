package cherhio.dao.impl;

import cherhio.dao.RouteImgDao;
import cherhio.domain.Route;
import cherhio.domain.RouteImg;
import cherhio.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author cherhio
 * @date Created in 2019-04-24 23:51
 */
public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "select * from tab_route_img where rid = ?";
        return template.query(sql,new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }
}
