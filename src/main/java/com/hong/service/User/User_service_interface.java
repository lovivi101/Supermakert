package com.hong.service.User;

import com.hong.pojo.User;

import java.util.List;

/**
 * @description: 业务登陆接口
 * @author: hjx
 * @time: 2021年03月17日 10:16
 */
public interface User_service_interface {
    //登陆 获取用户密码
    public User Login(String username);

    //更改密码
    public boolean update_password(String password , int id );

    //查询记录数
    public int getUserCount(String username, int userRole);

    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);
}
