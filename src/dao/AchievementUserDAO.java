package dao;

import entity.AchievementUser;
import entity.AchievementUserPK;

/**
 * Created by Donggu on 2016/12/5.
 */
public class AchievementUserDAO extends BaseDAO<AchievementUser> {
    private static AchievementUserDAO instance = new AchievementUserDAO();
    private AchievementUserDAO(){}

    public static AchievementUserDAO getInstance(){
        return instance;
    }

    public void delete(AchievementUserPK pk) {
        super.delete(AchievementUser.class, pk);
    }

    public AchievementUser findById(AchievementUserPK pk) {
        return super.findById(AchievementUser.class, pk);
    }
}
