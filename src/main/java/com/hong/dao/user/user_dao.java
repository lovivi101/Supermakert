package com.hong.dao.user;

import com.hong.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface user_dao {
    //获取用户登陆信息
    public User getLoginUser(Connection connection, String UserCode) throws SQLException;

    //更改用户密码
    public int update_password(Connection connection , String password , int id) throws SQLException;

    //根据用户名或者角色查询用户总数
    public int getUserCount(Connection connection,String username ,int userRole)throws SQLException, Exception;

    //通过条件查询-userList
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize)throws Exception;
}
