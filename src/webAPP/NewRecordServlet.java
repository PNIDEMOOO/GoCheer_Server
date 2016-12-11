package webAPP;

import dao.AchievementDAO;
import dao.AchievementUserDAO;
import dao.RecordDAO;
import dao.UserDAO;
import entity.AchievementUser;
import entity.Record;
import entity.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Donggu on 2016/12/11.
 */
@WebServlet(name = "NewRecordServlet")
public class NewRecordServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            // 没有登录
            return;
        }

        String word = request.getParameter("word");
        word = word.toLowerCase();
        Pattern exp = Pattern.compile("[a-z]+");
        Matcher matcher = exp.matcher(word);
        if (!matcher.find()) {
            return;
        }

        // 查了一个单词才加入记录
        if (matcher.groupCount() == 1) {
            RecordDAO.getInstance().save(new Record(matcher.group(0), user.getUsername()));
        }

        // 更新用户信息
        user.setScore(user.getScore() + 1);
        user.setWordsum(user.getWordsum() + 1);
        UserDAO.getInstance().update(user);

        // 查询成就
        ArrayList<Integer> newAchievement = user.checkAchievement();
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        if(newAchievement.isEmpty()){
           json.put("achievement",null);
        }
        else{
            for(Integer i : newAchievement){
                AchievementUserDAO.getInstance().save(new AchievementUser(i,user.getUsername()));
                array.add(AchievementDAO.getInstance().findById(i).JSONInfo());
            }
            json.put("achievement",array);
        }

        response.getWriter().append(json.toJSONString());
    }
}
