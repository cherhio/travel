package cherhio.service.impl;

import cherhio.dao.FavoriteDao;
import cherhio.dao.impl.FavoriteDaoImpl;
import cherhio.domain.Favorite;
import cherhio.service.FavoriteService;

/**
 * @author cherhio
 * @date Created in 2019-04-25 14:56
 */
public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(Integer.parseInt(rid), uid);

        //如果对象有值，则为true,没有，就是false
        return favorite != null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
