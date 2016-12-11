package entity;

import dao.AchievementDAO;
import dao.AchievementUserDAO;
import dao.BaseDAO;
import org.json.simple.JSONObject;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;

/**
 * Created by Donggu on 2016/12/10.
 */
@Entity
public class User {
    private String username;
    private String password;
    private boolean gender;
    private String alias;
    private Timestamp registertime;
    private String email;
    private int score;
    private int wordsum;
    private int scoresum;

    public User(){registertime = Timestamp.valueOf(LocalDateTime.now());}

    public User(String username, String password, boolean gender, String alias, String email) {
        this();
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.alias = alias;
        this.email = email;
    }

    public JSONObject JSONInfo(){
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("gender",gender);
        json.put("alias",alias);
        json.put("email",email);
        return json;
    }

    /**
     * check whether user gets new achievements
     * @return arraylist of new achievements
     */
    public ArrayList<Integer> checkAchievement(){
        ArrayList<Integer> newAchievements = new ArrayList<>();
        ArrayList<Integer> userAchievements = AchievementUserDAO.getInstance().findByUser(username);
        List achievements = BaseDAO.query("from Achievement");

        // 遍历查看
        for(Iterator it = achievements.iterator(); it.hasNext();){
            Achievement a = (Achievement)it.next();
            // 判断用户是否已获得该成就
            if(userAchievements.contains(a.getId())) continue;
            // 根据成就类型进行读取
            switch (a.getType()){
                // 查询次数达到一定数量
                case "wordsum":
                    if(this.wordsum == Integer.parseInt(a.getCondition())){
                        newAchievements.add(a.getId());
                    }
                    break;
                default:
            }
        }
        return newAchievements;
    }

    @Id
    @Column(name = "username", nullable = false, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "gender", nullable = false)
    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "alias", nullable = false, length = 255)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "registertime", nullable = false)
    public Timestamp getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Timestamp registertime) {
        this.registertime = registertime;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "score", nullable = false)
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Basic
    @Column(name = "wordsum", nullable = false)
    public int getWordsum() {
        return wordsum;
    }

    public void setWordsum(int wordsum) {
        this.wordsum = wordsum;
    }

    @Basic
    @Column(name = "scoresum", nullable = false)
    public int getScoresum() {
        return scoresum;
    }

    public void setScoresum(int scoresum) {
        this.scoresum = scoresum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (gender != user.gender) return false;
        if (score != user.score) return false;
        if (wordsum != user.wordsum) return false;
        if (scoresum != user.scoresum) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (alias != null ? !alias.equals(user.alias) : user.alias != null) return false;
        if (registertime != null ? !registertime.equals(user.registertime) : user.registertime != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username != null ? username.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (gender ? 1 : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (registertime != null ? registertime.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + wordsum;
        result = 31 * result + scoresum;
        return result;
    }
}
