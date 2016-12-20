package entity;

import dao.AchievementUserDAO;
import dao.BaseDAO;
import dao.RecordDAO;
import org.json.simple.JSONObject;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    /**
     * generate JSONObject for user info
     * @return user infomation (no password)
     */
    public JSONObject JSONInfo(){
        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("gender",gender);
        json.put("alias",alias);
        json.put("email",email);
        json.put("registerTime",registertime.toString());
        json.put("score",score);
        json.put("wordsum",wordsum);
        json.put("scoresum",scoresum);
        return json;
    }

    /**
     * check whether user gets new achievements
     * @param newRecord new record of searching word (null maybe)
     * @return arraylist of new achievements
     */
    public ArrayList<Integer> checkAchievement(Record newRecord){
        ArrayList<Integer> newAchievements = new ArrayList<>();
        List achievements = BaseDAO.query("from Achievement");
        List notUserAchievements = AchievementUserDAO.getInstance().getNotUserAchievements(this.username);

        // 遍历查看
        for(Iterator it = notUserAchievements.iterator(); it.hasNext();){
            Achievement a = (Achievement)it.next();
            // 根据成就类型进行读取
            switch (a.getType()){
                // 查询次数达到指定数量
                case "wordsum":
                    if(this.wordsum >= Integer.parseInt(a.getCondition())){
                        newAchievements.add(a.getId());
                    }
                    break;
                // 查询某些特定单词
                case "specific word":
                    if(newRecord!=null&&newRecord.getWord().equals(a.getCondition())){
                        newAchievements.add(a.getId());
                    }
                    break;
                //在特定的星期几刷词获得成就
                case "day":
                    if(newRecord!=null)
                    {
                        String dayOfWeek=newRecord.getDatetime().toLocalDateTime().getDayOfWeek().toString();
                        if(dayOfWeek.equals(a.getCondition()))
                            newAchievements.add(a.getId());
                    }
                    break;
                //在特定的日期刷词获得成就，一月一号在record的condition中存为0101
                case "date":
                    if(newRecord!=null)
                    {
                        int month=newRecord.getDatetime().toLocalDateTime().getMonthValue();
                        int day=newRecord.getDatetime().toLocalDateTime().getDayOfMonth();
                        int m=Integer.parseInt(a.getCondition().substring(0,2));
                        int d=Integer.parseInt(a.getCondition().substring(2,4));
                        if(month==m&&day==d)
                            newAchievements.add(a.getId());
                    }
                    break;
                //在特定的时间点刷词获得成就
                case "time":
                    if(newRecord!=null&&newRecord.getDatetime().toLocalDateTime().getHour()==Integer.parseInt(a.getCondition()))
                    {
                        newAchievements.add(a.getId());
                    }
                    break;
                //在某个时间段重复查一个单词获得成就
                // TODO: 感觉可以研究一下sql能不能完成日期比较（毕竟datetime是内置类型）。
                case "duration & repeat count":
                    if(newRecord!=null)
                    {
                        List userHistory=RecordDAO.getInstance().getUserHistory(username,newRecord.getWord());

                        int count=0;
                        LocalDateTime t=newRecord.getDatetime().toLocalDateTime().minusHours(Long.parseLong(a.getCondition()));

                        for(Iterator i=userHistory.iterator();i.hasNext();)
                        {
                            Record r=(Record)i.next();

                            if(r.getDatetime().toLocalDateTime().isAfter(t))
                                count++;
                        }

                        if(count==Integer.parseInt(a.getCondition2()))
                            newAchievements.add(a.getId());
                    }
                    break;
                //重复刷某个词累计达到一个数值获得成就
                // TODO: 算法优化：可以直接使用sql聚合函数count(*)获得数量
                case "repeat count":
                    if(newRecord!=null)
                    {
                        List userHistory=RecordDAO.getInstance().getUserHistory(username,newRecord.getWord());

                        int count=0;
                        for(Iterator i=userHistory.iterator();i.hasNext();)
                            count++;

                        if(count==Integer.parseInt(a.getCondition()))
                            newAchievements.add(a.getId());
                    }
                    break;
                //在特定的星期几的某个时刻划词获得成就
                // TODO: 不必检测newrecord非空，因为不是所有的查询都有record产生，但可以计入成就。
                case "day & time":
                    if(newRecord!=null)
                    {
                        String dayOfWeek=newRecord.getDatetime().toLocalDateTime().getDayOfWeek().toString();

                        if(dayOfWeek.equals(a.getCondition())&&newRecord.getDatetime().toLocalDateTime().getHour()==Integer.parseInt(a.getCondition()))
                            newAchievements.add(a.getId());
                    }
                    break;
                //在特定的日期划到了一定量的词获得成就
                case "date & wordsum":
                    if(newRecord!=null)
                    {
                        int month=newRecord.getDatetime().toLocalDateTime().getMonthValue();
                        int day=newRecord.getDatetime().toLocalDateTime().getDayOfMonth();
                        int m=Integer.parseInt(a.getCondition().substring(0,2));
                        int d=Integer.parseInt(a.getCondition().substring(2,4));
                        if(month==m&&day==d&&this.wordsum >= Integer.parseInt(a.getCondition()))
                            newAchievements.add(a.getId());
                    }
                    break;
                //在某个时刻划到了一定量的词获得成就
                case "time & wordsum":
                    if(newRecord!=null)
                    {
                        if(newRecord.getDatetime().toLocalDateTime().getHour()==Integer.parseInt(a.getCondition())&&this.wordsum >= Integer.parseInt(a.getCondition()))
                        {
                            newAchievements.add(a.getId());
                        }
                    }
                    break;
                //在特定的日期时间划词获得了成就
                case "date & time":
                    if(newRecord!=null)
                    {
                        int month=newRecord.getDatetime().toLocalDateTime().getMonthValue();
                        int day=newRecord.getDatetime().toLocalDateTime().getDayOfMonth();
                        int m=Integer.parseInt(a.getCondition().substring(0,2));
                        int d=Integer.parseInt(a.getCondition().substring(2,4));
                        if(month==m&&day==d&&newRecord.getDatetime().toLocalDateTime().getHour()==Integer.parseInt(a.getCondition()))
                            newAchievements.add(a.getId());
                    }
                    break;
                //在特定的日子刷到了特定的词语获得成就
                case "date & specific word":
                    if(newRecord!=null)
                    {
                        int month=newRecord.getDatetime().toLocalDateTime().getMonthValue();
                        int day=newRecord.getDatetime().toLocalDateTime().getDayOfMonth();
                        int m=Integer.parseInt(a.getCondition().substring(0,2));
                        int d=Integer.parseInt(a.getCondition().substring(2,4));
                        if(month==m&&day==d&&newRecord.getWord().equals(a.getCondition()))
                            newAchievements.add(a.getId());
                    }
                    break;
                //在指定时间内查够了一定量的单词获得成就
                case "duration & wordsum":
                    if(newRecord!=null)
                    {
                        List userHistory=RecordDAO.getInstance().getUserHistory(username);

                        int sum=0;
                        LocalDateTime t=newRecord.getDatetime().toLocalDateTime().minusHours(Long.parseLong(a.getCondition()));

                        for(Iterator i=userHistory.iterator();i.hasNext();)
                        {
                            Record r=(Record)i.next();

                            if(r.getDatetime().toLocalDateTime().isAfter(t))
                                sum++;
                        }

                        if(sum==Integer.parseInt(a.getCondition2()))
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
    public boolean isGender() {
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
