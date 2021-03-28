package com.hong.dao.bill;

import com.hong.dao.BaseDao;
import com.hong.pojo.Bill;
import com.mysql.cj.util.StringUtils;

import java.awt.image.BandedSampleModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: hjx
 * @time: 2021年03月21日 9:12
 */
public class bill_dao_implements implements bill_dao{
    
    /** 
    * @Description:  增加订单
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public int add(Connection connection, Bill bill) throws Exception {
        PreparedStatement ps =null;
        int flag = 0;
        if (null!=connection){
            String sql = "insert into smbms_bill (billCode,productName,productDesc," +
                    "productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {bill.getBillCode(),bill.getProductName(),bill.getProductDesc(),
                    bill.getProductUnit(),bill.getProductCount(),bill.getTotalPrice(),bill.getIsPayment(),
                    bill.getProviderId(),bill.getCreatedBy(),bill.getCreationDate()};
            flag = BaseDao.update(connection,ps, sql , params);
            System.out.println("dao-----flag:"+flag);
        }
        return flag;
    }

    
    /** 
    * @Description: 通过查询条件 模糊查询供应商列表
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public List<Bill> getBillList(Connection connection, Bill bill) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Bill> billList = new ArrayList<>();
        if (null !=connection){

            StringBuffer sql = new StringBuffer();
            sql.append("select b.*,p.proName as providerName from smbms_bill b, smbms_provider p where b.providerId = p.id");
            List<Object> list = new ArrayList<Object>();

            if(!StringUtils.isNullOrEmpty(bill.getProductName())){
                sql.append(" and productName like ?");
                list.add("%"+bill.getProductName()+"%");
            }
            if(bill.getProviderId() > 0){
                sql.append(" and providerId = ?");
                list.add(bill.getProviderId());
            }
            if(bill.getIsPayment() > 0){
                sql.append(" and isPayment = ?");
                list.add(bill.getIsPayment());
            }
            Object[] params = list.toArray();
            System.out.println("sql --------- > " + sql.toString());

            ResultSet execute = BaseDao.execute(connection, rs, ps, sql.toString(), params);
            if (execute.next()){
                Bill _bill = new Bill();
                _bill.setId(rs.getInt("id"));
                _bill.setBillCode(rs.getString("billCode"));
                _bill.setProductName(rs.getString("productName"));
                _bill.setProductDesc(rs.getString("productDesc"));
                _bill.setProductUnit(rs.getString("productUnit"));
                _bill.setProductCount(rs.getBigDecimal("productCount"));
                _bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                _bill.setIsPayment(rs.getInt("isPayment"));
                _bill.setProviderId(rs.getInt("providerId"));
                _bill.setProviderName(rs.getString("providerName"));
                _bill.setCreationDate(rs.getTimestamp("creationDate"));
                _bill.setCreatedBy(rs.getInt("createdBy"));
                billList.add(_bill);
            }
            BaseDao.closeResource(connection,rs,ps);
        }
        return billList;
    }

    
    /** 
    * @Description: 通过ID delete  bill 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public int deleteBillById(Connection connection, String delId) throws Exception {
        PreparedStatement ps = null ;
        List<Bill> billList = new ArrayList<>();

        int flag = 0;
        if (null!=connection){
            String sql = "delete from smbms_bill where id=?";
            Object[] params ={delId};
            flag = BaseDao.update(connection, ps, sql, params);
            BaseDao.closeResource(connection,null,ps);
        }
        return flag;
    }


    /**
    * @Description: 通过Id 查询bill
    * @Param:  * @param null
    * @return:
    * @Author: hjx
    * @Date: 2021/3/22
    */
    @Override
    public Bill getBillById(Connection connection, String id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Bill bill = null;
        if (null!=connection){
            String sql = "Select from smbms_bill where id =?";
            Object[] params = {id};
            ResultSet resultSet = BaseDao.execute(connection, rs, ps, sql, params);
            if(rs.next()){
                bill = new Bill();
                bill.setId(rs.getInt("id"));
                bill.setBillCode(rs.getString("billCode"));
                bill.setProductName(rs.getString("productName"));
                bill.setProductDesc(rs.getString("productDesc"));
                bill.setProductUnit(rs.getString("productUnit"));
                bill.setProductCount(rs.getBigDecimal("productCount"));
                bill.setTotalPrice(rs.getBigDecimal("totalPrice"));
                bill.setIsPayment(rs.getInt("isPayment"));
                bill.setProviderId(rs.getInt("providerId"));
                bill.setProviderName(rs.getString("providerName"));
                bill.setModifyBy(rs.getInt("modifyBy"));
                bill.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(connection,rs , ps);
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
    public int modify(Connection connection, Bill bill) throws Exception {
        PreparedStatement ps = null;
        int flag = 0;
        if (null!=connection){
            String sql = "insert into smbms_bill (billCode,productName,productDesc," +
                    "productUnit,productCount,totalPrice,isPayment,providerId,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {bill.getBillCode(),bill.getProductName(),bill.getProductDesc(),
                    bill.getProductUnit(),bill.getProductCount(),bill.getTotalPrice(),bill.getIsPayment(),
                    bill.getProviderId(),bill.getCreatedBy(),bill.getCreationDate()};
            flag = BaseDao.update(connection,ps, sql , params);
            BaseDao.closeResource(connection,null , ps );
        }

        return flag;
    }

    /** 
    * @Description: 根据供应商ID查询订单数量
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public int getBillCountByProviderId(Connection connection, String providerId) throws Exception {
        int count = 0;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if(null != connection){
            String sql = "select count(1) as billCount from smbms_bill where" +
                    "	providerId = ?";
            Object[] params = {providerId};
            rs = BaseDao.execute(connection, rs, pstm, sql, params);
            if(rs.next()){
                count = rs.getInt("billCount");
            }
            BaseDao.closeResource(null, rs, pstm);
        }

        return count;
    }
}
