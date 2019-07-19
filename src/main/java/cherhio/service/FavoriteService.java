package cherhio.service;

/**
 * @author cherhio
 * @date Created in 2019-04-25 14:55
 */
public interface FavoriteService {

    /**
     * 判断是否有收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(String rid,int uid);

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);
}
