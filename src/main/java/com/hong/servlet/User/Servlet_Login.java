package com.hong.servlet.User;

import com.hong.pojo.User;
import com.hong.service.User.user_service_implements;
import com.hong.service.User.User_service_interface;
import com.hong.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @Description: 控制层 Servlet 调用业务层 实现用户登陆密码 是否成功
* @Param:
* @return:
* @Author: hjx
* @Date: 2021/3/17
*/

@WebServlet(name = "Servlet_Login", value = "/Servlet_Login")
public class Servlet_Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        //1 获取 用户名 用户密码
        String userCode = request.getParameter("userCode");
        String userPassword = request.getParameter("userPassword");


//        String code = (String) request.getSession().getAttribute("userCode");
//        String pwd = (String) request.getSession().getAttribute("userPassword");
//        System.out.println(code +" :" +pwd);
        //2 获取业务层 匹配用户密码
        User_service_interface User_service = new user_service_implements();
        User user = User_service.Login(userCode);
        if (user.getUserPassword().equals(userPassword))//3 . 登陆成功 转发到登陆页面
        {
            request.getSession().setAttribute(Constants.USER_SESSION,user);
            response.sendRedirect("jsp/frame.jsp");
        }
        else { //4 登陆失败 重定向到登陆用户页面
            request.setAttribute("error" , "密码错误 请重新输入");
            request.getRequestDispatcher("login.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        doGet(request, response);
    }
}
