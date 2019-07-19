package cherhio.dao;

import cherhio.domain.Seller;

/**
 * @author cherhio
 * @date Created in 2019-04-24 23:56
 */
public interface SellerDao {
    /**
     * 根据id查询
     * @param id
     * @return
     */
    public Seller findById(int id);
}
