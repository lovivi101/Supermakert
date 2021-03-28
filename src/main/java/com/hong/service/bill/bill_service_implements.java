package com.hong.service.bill;

import com.hong.dao.BaseDao;
import com.hong.dao.bill.bill_dao;
import com.hong.dao.bill.bill_dao_implements;
import com.hong.pojo.Bill;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @author: hjx
 * @time: 2021年03月21日 9:14
 */
public class bill_service_implements implements bill_service{

    private bill_dao billDao;

    public bill_service_implements(){
        this.billDao = new bill_dao_implements();
    }

    /**
    * @Description: 增加订单
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/22
    */
    @Override
    public boolean add(Bill bill) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务管理
            if(billDao.add(connection,bill) > 0)
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

            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    
    /** 
    * @Description: 通过条件 查询订单列表 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public List<Bill> getBillList(Bill bill) {
        Connection connection = null;
        List<Bill> billList = null;
        System.out.println("query productName ---- > " + bill.getProductName());
        System.out.println("query providerId ---- > " + bill.getProviderId());
        System.out.println("query isPayment ---- > " + bill.getIsPayment());

        try {
            connection = BaseDao.getConnection();
            billList = billDao.getBillList(connection, bill);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return billList;
    }

    
    /** 
    * @Description: 通过订单id删除订单 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public boolean deleteBillById(String delId) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if(billDao.deleteBillById(connection, delId) > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }

    /** 
    * @Description: 通过订单id获取订单 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public Bill getBillById(String id) {
        Bill bill = null;
        Connection connection = null;
        try{
            connection = BaseDao.getConnection();
            bill = billDao.getBillById(connection, id);
        }catch (Exception e) {
            e.printStackTrace();
            bill = null;
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return bill;
    }

    
    /** 
    * @Description: 通过订单 修改订单信息 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public boolean modify(Bill bill) {
        Connection connection = null;
        boolean flag = false;
        try {
            connection = BaseDao.getConnection();
            if(billDao.modify(connection,bill) > 0)
                flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            BaseDao.closeResource(connection, null, null);
        }
        return flag;
    }
}
