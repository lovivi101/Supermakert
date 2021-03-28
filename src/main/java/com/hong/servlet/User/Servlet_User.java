package com.hong.servlet.User;

import com.alibaba.fastjson.JSON;
import com.hong.pojo.User;
import com.hong.pojo.role;
import com.hong.service.role.Role_service_interface;
import com.hong.service.role.Role_service_implements;
import com.hong.service.User.user_service_implements;
import com.hong.service.User.User_service_interface;
import com.hong.util.Constants;
import com.hong.util.PageSupport;
import com.mysql.cj.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "Servlet_User", value = "/jsp/user.do")
public class Servlet_User extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("接收到");
        String method = (String) request.getAttribute("method");
        System.out.println(method);
        System.out.println(method);
        if (method.equals("savepwd") && method!=null){
            this.updatePwd(request ,response);
        }
        else if (method.equals("pwdmodify")&&method!=null){
            this.Verify_Password(request,response);
        }
        else if (method.equals("query")&&method!=null){
            this.query(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
    * @Description: 修改新密码
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/19
    */
    public void updatePwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Object id = request.getSession().getId();
        String newpassword = (String) request.getAttribute("newpassword");
        boolean flag = false;
        if (id!=null && newpassword!=null){
            User_service_interface user_service = new user_service_implements();
            flag = user_service.update_password(newpassword, ((User) id).getId());
            if (flag){
                request.setAttribute("message" , "密码修改成功 请退出 重新登陆密码");
                request.getSession().removeAttribute(Constants.USER_SESSION);
                request.getRequestDispatcher("login.jsp");
            }
            else {
                request.setAttribute("message" , "密码修改失败 请重新修改密码");
            }
        }
        else {
            request.setAttribute("message" , "新密码错误 请重新修改新密码");
        }
        System.out.println(((User)id).getUserName()+" :"+((User)id).getUserPassword());
    }
    /**
    * @Description: 验证旧密码是否正确
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/19
    */

    public void Verify_Password(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        Object o = request.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = (String) request.getAttribute("oldpassword");

        Map<String , String> map = new HashMap<>();
        if(o==null){
            map.put("result", "sessionerror");
        }
        else if (StringUtils.isNullOrEmpty(oldpassword)){
            map.put("result", "error");
        }
        else {
            String password = ((User) o).getUserPassword();
            if (oldpassword.equals(password)){
                map.put("result", "true");
            }
            else {
                map.put("result", "false");
            }
        }
        response.setContentType("application/josn");
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(map));
        writer.flush();
        writer.close();
    }

    public void query(HttpServletRequest request, HttpServletResponse response){
        String queryUserName = request.getParameter("queryname");
        String temp = request.getParameter("queryUserRole");
        String pageIndex = request.getParameter("pageIndex");
        int queryUserRole = 0;

        //获取用户列表
        User_service_interface userService = new user_service_implements();
        List<User> userList = null;

        //第一此请求肯定是走第一页，页面大小固定的
        //设置页面容量
        int pageSize = 5;//把它设置在配置文件里,后面方便修改
        //当前页码
        int currentPageNo = 1;

        if(queryUserName == null){
            queryUserName = "";
        }
        if(temp != null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp);
        }
        if(pageIndex != null) {
            currentPageNo = Integer.parseInt(pageIndex);
        }
        //获取用户总数（分页	上一页：下一页的情况）
        //总数量（表）
        int totalCount	= userService.getUserCount(queryUserName,queryUserRole);

        //总页数支持
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);

        int totalPageCount =pageSupport.getTotalPageCount();//总共有几页
        //(totalCount+pageSize-1/pageSize)取整
        // pageSupport.getTotalCount()

        //System.out.println("totalCount ="+totalCount);
        //System.out.println("pageSize ="+pageSize);
        //System.out.println("totalPageCount ="+totalPageCount);
        //控制首页和尾页
        //如果页面小于 1 就显示第一页的东西
        if(currentPageNo < 1) {
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount) {//如果页面大于了最后一页就显示最后一页
            currentPageNo =totalPageCount;
        }

        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        request.setAttribute("userList", userList);

        Role_service_interface roleService = new Role_service_implements();
        List<role> roleList = roleService.getRoleList();
        request.setAttribute("roleList", roleList);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("currentPageNo", currentPageNo);
        request.setAttribute("totalPageCount", totalPageCount);
        request.setAttribute("queryUserName", queryUserName);
        request.setAttribute("queryUserRole", queryUserRole);

        //返回前端
        try {
            request.getRequestDispatcher("userlist.jsp").forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
