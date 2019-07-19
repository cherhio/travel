package cherhio.dao.impl;

import cherhio.dao.CategoryDao;
import cherhio.domain.Category;
import cherhio.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * @author cherhio
 * @date Created in 2019-04-23 17:08
 */
public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<Category> findAll() {
        String sql = "select * from tab_category";

        return template.query(sql,new BeanPropertyRowMapper<>(Category.class));
    }
}
