package webAPP;

import dao.RecordDAO;
import dao.UserDAO;
import entity.Record;
import entity.User;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * Created by orient on 2016/12/8.
 */
@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String gender=request.getParameter("gender");
        String alias=request.getParameter("alias");
        String email=request.getParameter("email");
//
//        List user = session.createQuery("from User where username='" +username+"' and password = '"+ password + "'").list();
//        if(user.isEmpty() || user==null){
//            response.sendRedirect("/error.jsp");
//        }
//        else{
//            HttpSession httpSession = request.getSession();
//            httpSession.setAttribute("user", user.get(0));
//            Cookie cookie = new Cookie("sessionId", httpSession.getId());
//            response.addCookie(cookie);
//            request.getRequestDispatcher("/hello.jsp").forward(request,response);
//        }
        User user = UserDAO.getInstance().findById(username);
        if(user != null){
            response.sendRedirect("/error.jsp");
        }
        else{

           // user=UserDAO.getInstance().save(new User(username,password,));
            boolean gender_bool;
            if(Integer.parseInt(gender)==1)
                gender_bool=true;
            else gender_bool=false;

            User user1=new User(username,password,gender_bool,alias,email);
            UserDAO.getInstance().save(user1);

            if(UserDAO.getInstance().findById(username)==null)
                response.sendRedirect("/error.jsp");
            else {
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("user", user1);
                Cookie cookie = new Cookie("sessionId", httpSession.getId());
                response.addCookie(cookie);
                request.getRequestDispatcher("hello.jsp").forward(request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/register.jsp");
    }

}
