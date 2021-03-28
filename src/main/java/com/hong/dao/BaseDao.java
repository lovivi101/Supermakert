package com.hong.dao;

import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @description: 数据库公共类
 * @author: hjx
 * @time: 2021年03月16日 15:06
 */
public class BaseDao {


    private static String driver ;
    private static String url ;
    private static String username ;
    private static String password ;

    static{//静态代码块,在类加载的时候执行
        init();
    }
    /**
    * @Description: 加载数据库驱动
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/16
    */
    public static void init() {
        Properties properties = new Properties();
        InputStream inputStream = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }

    @Test
    public void test(){
//        init();
        System.out.println(driver);
        System.out.println(url);
        System.out.println(username);
        System.out.println(password);
    }

    /**
    * @Description: 连接数据库
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/16
    */
    public static Connection getConnection(){
//        init();
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }
    /**
    * @Description: sql 查询操作
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/16
    */
    public static ResultSet execute(Connection connection , ResultSet resultSet , PreparedStatement preparedStatement ,
                                    String sql , Object[] params) throws SQLException {
        preparedStatement=connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i+1 , params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet ;
    }

    /**
    * @Description: sql 更新操作
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/16
    */
    public static int update(Connection connection  , PreparedStatement preparedStatement ,
                             String sql, Object[] params)throws SQLException{
        int resultSet = 0 ;
        preparedStatement=connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i+1 , params[i]);
        }
        resultSet = preparedStatement.executeUpdate();
        return resultSet ;
    }

    
    /** 
    * @Description: 关闭资源 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/16 
    */
    public static boolean closeResource(Connection connection , ResultSet resultSet ,
                                        PreparedStatement preparedStatement){
        boolean flag = true;
        if (connection!=null){
            try {
                connection.close();
                connection=null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if (resultSet!=null){
            try {
                resultSet.close();
                resultSet=null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
                preparedStatement=null;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
