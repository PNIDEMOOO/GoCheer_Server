package webAPP;

import dao.UserDAO;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Donggu on 2016/12/17.
 */
@WebServlet(name = "HomeServlet")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String targetUsername = request.getRequestURI().substring(6);
        User currentUser = (User)request.getSession().getAttribute("user");
        if(targetUsername.isEmpty()){
            response.sendRedirect(currentUser==null?"/login.jsp":"/home/"+currentUser.getUsername());
            return;
        }
        User targetUser = UserDAO.getInstance().findById(targetUsername);
        if(targetUser==null){
            response.sendRedirect("error.jsp");
        }
        else{
            request.setAttribute("targetUser", targetUser);
            request.setAttribute("isSelf",(currentUser!=null&&currentUser.equals(targetUser)));
            request.getRequestDispatcher("home.jsp").forward(request,response);
        }
    }
}
