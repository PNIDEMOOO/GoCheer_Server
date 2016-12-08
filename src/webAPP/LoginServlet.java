package webAPP;

import dao.UserDAO;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by Donggu on 2016/12/3.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = UserDAO.getInstance().findById(username,password);
        if(user == null){
            response.sendRedirect("/error.jsp");
        }
        else{
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user",user);
            Cookie cookie = new Cookie("sessionId", httpSession.getId());
            response.addCookie(cookie);
            request.getRequestDispatcher("hello.jsp").forward(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/Login.jsp");
    }
}
