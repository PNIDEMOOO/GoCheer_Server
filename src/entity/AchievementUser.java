package entity;

import javax.persistence.*;

/**
 * Created by Donggu on 2016/12/5.
 */
@Entity
@Table(name = "achievement_user", schema = "javaEE", catalog = "")
@IdClass(AchievementUserPK.class)
public class AchievementUser {
    private int achievement;
    private String user;

    public AchievementUser(){}
    public AchievementUser(int achievement, String user){
        this.achievement=achievement;
        this.user=user;
    }

    @Id
    @Column(name = "achievement", nullable = false)
    public int getAchievement() {
        return achievement;
    }

    public void setAchievement(int achievement) {
        this.achievement = achievement;
    }

    @Id
    @Column(name = "user", nullable = false, length = 255)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AchievementUser that = (AchievementUser) o;

        if (achievement != that.achievement) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = achievement;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
