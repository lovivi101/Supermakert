package com.hong.service.provider;

import com.hong.dao.BaseDao;
import com.hong.dao.bill.bill_dao;
import com.hong.dao.bill.bill_dao_implements;
import com.hong.dao.provider.provider_dao;
import com.hong.dao.provider.provider_dao_implements;
import com.hong.pojo.provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @author: hjx
 * @time: 2021年03月21日 9:13
 */
public class provider_service_implements implements provider_service{


    private provider_dao providerDao;
    private bill_dao billDao;
    public provider_service_implements(){
        this.providerDao=new provider_dao_implements();
        this.billDao=new bill_dao_implements();
    }
    /**
    * @Description: 增加供应商
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/22
    */
    @Override
    public boolean add(provider provider) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            if(providerDao.add(connection,provider) > 0)
                flag = true;
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println("rollback==================");
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally{
            //在service层进行connection连接的关闭
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    /**
    * @Description: 通过供应商名称、编码获取供应商列表-模糊查询-providerList
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/22
    */
    @Override
    public List<provider> getProviderList(String proName, String proCode) {
        Connection connection = null;
        List<provider> providerList = null;
        System.out.println("query proName ---- > " + proName);
        System.out.println("query proCode ---- > " + proCode);
        try {
            connection = BaseDao.getConnection();
            providerList = providerDao.getProviderList(connection, proName,proCode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return providerList;
    }

    /**
    * @Description: 通过proId删除Provider
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/22
    */
    @Override
    public int deleteProviderById(String delId) {
        Connection connection = null;
        int billCount = -1;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);
            billCount = billDao.getBillCountByProviderId(connection,delId);
            if(billCount == 0){
                providerDao.deleteProviderById(connection, delId);
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            billCount = -1;
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return billCount;
    }

    /**
    * @Description: 通过proId获取Provider
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/22
    */
    @Override
    public provider getProviderById(String id) {
        provider provider = null;
        Connection connection = null;
        try{
            connection = BaseDao.getConnection();
            provider = providerDao.getProviderById(connection, id);
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            provider = null;
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return provider;
    }

    /**
    * @Description: 修改用户信息
     * @Param:
     * * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/22
    */
    @Override
    public boolean modify(provider provider) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if(providerDao.modify(connection,provider) > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
}
