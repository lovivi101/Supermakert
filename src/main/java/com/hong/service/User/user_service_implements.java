package com.hong.service.User;

import com.hong.dao.BaseDao;
import com.hong.dao.user.user_dao;
import com.hong.dao.user.user_dao_Implements;
import com.hong.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @description: 业务登陆实现类
 * @author: hjx
 * @time: 2021年03月17日 10:16
 */
public class user_service_implements implements User_service_interface {

    private user_dao userdao ;
    public user_service_implements(){
        userdao = new user_dao_Implements();
    }

    /**
    * @Description: 登陆用户 获取信息 密码是否匹配
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/19
    */
    @Override
    public User Login(String username) {
        Connection connection = null ;
        User user = null ;
        try {
            connection = BaseDao.getConnection();
            user = userdao.getLoginUser(connection, username);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            BaseDao.closeResource(connection,null,null);
        }
        return user;
    }

    /** 
    * @Description: 调用dao层 实现密码更改 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/19 
    */
    @Override
    public boolean update_password(String password, int id) {
        Connection connection = null ;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            int i = userdao.update_password(connection, password, id);
            if (i >0){
                flag =true ;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    @Override
    public int getUserCount(String queryUserName, int queryUserRole) {
        Connection connection = null;
        int count = 0;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        user_dao user_dao = new user_dao_Implements();
        try {
            connection = BaseDao.getConnection();
            count = user_dao.getUserCount(connection, queryUserName,queryUserRole);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        //System.out.println("count"+count);
        return count;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;
        System.out.println("queryUserName ---- > " + queryUserName);
        System.out.println("queryUserRole ---- > " + queryUserRole);
        System.out.println("currentPageNo ---- > " + currentPageNo);
        System.out.println("pageSize ---- > " + pageSize);
        user_dao user_dao = new user_dao_Implements();
        try {
            connection = BaseDao.getConnection();
            userList = user_dao.getUserList(connection, queryUserName,queryUserRole,currentPageNo,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }
}
