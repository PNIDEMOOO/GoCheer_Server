package webAPP;

import Model.BeanConverter;
import entity.User;
import jdk.nashorn.internal.ir.debug.JSONWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Donggu on 2016/12/10.
 */
@WebServlet(name = "UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User)request.getSession().getAttribute("user");
        JSONObject json = new JSONObject();
        if(user==null){
            json.put("user", null);
        }
        else {
            JSONObject userinfo = BeanConverter.toJSON(user);
            userinfo.remove("password");
            json.put("user",userinfo);
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().append(json.toJSONString());
    }
}
