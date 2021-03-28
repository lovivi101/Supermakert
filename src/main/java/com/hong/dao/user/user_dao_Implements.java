package com.hong.dao.user;

import com.hong.dao.BaseDao;
import com.hong.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 查询用户名
 * @author: hjx
 * @time: 2021年03月17日 9:30
 */
public class user_dao_Implements implements user_dao {

    /**
    * @Description: 获取用户信息 登陆用户信息
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/19
    */
    @Override
    public User getLoginUser(Connection connection, String UserCode) throws SQLException {

        //1 . 初始化三个对象
        PreparedStatement ps = null;
        ResultSet rs = null ;
        User user = null ;

        //2 . 连接数据库不为空 执行查询
        if (null!=connection){
            String sql = "Select * from smbms_user where userCode=?";
            Object[] params = {UserCode};
            rs = BaseDao.execute(connection , rs , ps , sql , params);
            if (rs.next()){
                // 3 获取 user对象数据
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            // 4 关闭资源
            BaseDao.closeResource(null,rs ,ps );
        }
        return user;
    }

    /**
    * @Description: 更改数据库的密码
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/19
    */
    @Override
    public int update_password(Connection connection, String password , int id) throws SQLException {
        PreparedStatement ps = null;
        int i = 0;
        System.out.println(password+" :"+id);
        if (connection!=null){
            String sql = "update smbms_user set userPassword = ? where id =?";
            Object[] params = {password , id};
            i = BaseDao.update(connection, ps, sql, params);

            BaseDao.closeResource(connection,null,ps);
        }


        return i;
    }

    @Override
    public int getUserCount(Connection connection, String username, int userRole) throws SQLException, Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName like ?");
                list.add("%"+username+"%");
            }
            if(userRole > 0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, rs,pstm,sql.toString(), params);
            if(rs.next()){
                count = rs.getInt("count");
            }
            BaseDao.closeResource(null, rs, pstm);
        }
        return count;
    }

    @Override
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(userName)){
                sql.append(" and u.userName like ?");
                list.add("%"+userName+"%");
            }
            if(userRole > 0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }
            //在数据库中，分页显示 limit startIndex，pageSize；总数
            //当前页  (当前页-1)*页面大小
            //0,5	1,0	 01234
            //5,5	5,0	 56789
            //10,5	10,0 10~
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());

            rs = BaseDao.execute(connection, rs, pstm, sql.toString(), params);
            while(rs.next()){
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResource(null, rs, pstm);
        }
        return userList;
    }
}
