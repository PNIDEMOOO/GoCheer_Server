package webAPP;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Donggu on 2016/11/8.
 */
//@WebServlet(name = "HelloServlet")
public class HelloServlet extends HttpServlet {

    public HelloServlet(){
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String stra = request.getParameter("numa");
        String strb = request.getParameter("numb");

        int a = Integer.parseInt(stra);
        int b = Integer.parseInt(strb);

        response.getWriter().append(a+" + "+b+" = "+ (a+b));
    }
}
