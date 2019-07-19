package cherhio.dao;

import cherhio.domain.Category;

import java.util.List;

/**
 * @author cherhio
 * @date Created in 2019-04-23 17:07
 */
public interface CategoryDao {

    public List<Category> findAll();
}
