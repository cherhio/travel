package cherhio.dao.impl;

import cherhio.dao.SellerDao;
import cherhio.domain.Seller;
import cherhio.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author cherhio
 * @date Created in 2019-04-24 23:56
 */
public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller findById(int id) {
        String sql ="select * from tab_seller where sid = ?";

        return template.queryForObject(sql,new BeanPropertyRowMapper<>(Seller.class),id);
    }
}
