package cherhio.dao;

import cherhio.domain.Favorite;

/**
 * @author cherhio
 * @date Created in 2019-04-25 14:56
 */
public interface FavoriteDao {
    /**
     * 根据rid和uid查询收藏信息
     * @param rid
     * @param uid
     * @return
     */
    public Favorite findByRidAndUid(int rid, int uid);

    int findCountByRid(int rid);

    void add(int rid, int uid);
}
