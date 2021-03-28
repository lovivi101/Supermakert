package com.hong.dao.provider;

import com.hong.dao.BaseDao;
import com.hong.dao.bill.bill_dao;
import com.hong.dao.bill.bill_dao_implements;
import com.hong.pojo.provider;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: hjx
 * @time: 2021年03月21日 9:12
 */
public class provider_dao_implements implements provider_dao{


    /** 
    * @Description: 增加供应商 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public int add(Connection connection, provider provider) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if(null != connection){
            String sql = "insert into smbms_provider (proCode,proName,proDesc," +
                    "proContact,proPhone,proAddress,proFax,createdBy,creationDate) " +
                    "values(?,?,?,?,?,?,?,?,?)";
            Object[] params = {provider.getProCode(),provider.getProName(),provider.getProDesc(),
                    provider.getProContact(),provider.getProPhone(),provider.getProAddress(),
                    provider.getProFax(),provider.getCreatedBy(),provider.getCreationDate()};
            flag = BaseDao.update(connection, pstm, sql, params);
            BaseDao.closeResource(connection, null, pstm);
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
    public List<provider> getProviderList(Connection connection, String proName, String proCode) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<provider> providerList = new ArrayList<provider>();
        if(connection != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select * from smbms_provider where 1=1 ");
            List<Object> list = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(proName)){
                sql.append(" and proName like ?");
                list.add("%"+proName+"%");
            }
            if(!StringUtils.isNullOrEmpty(proCode)){
                sql.append(" and proCode like ?");
                list.add("%"+proCode+"%");
            }
            Object[] params = list.toArray();
            System.out.println("sql ----> " + sql.toString());
            rs = BaseDao.execute(connection, rs, pstm, sql.toString(), params);
            while(rs.next()){
                provider _provider = new provider();
                _provider.setId(rs.getInt("id"));
                _provider.setProCode(rs.getString("proCode"));
                _provider.setProName(rs.getString("proName"));
                _provider.setProDesc(rs.getString("proDesc"));
                _provider.setProContact(rs.getString("proContact"));
                _provider.setProPhone(rs.getString("proPhone"));
                _provider.setProAddress(rs.getString("proAddress"));
                _provider.setProFax(rs.getString("proFax"));
                _provider.setCreationDate(rs.getTimestamp("creationDate"));
                providerList.add(_provider);
            }
            BaseDao.closeResource(connection, rs, pstm);
        }
        return providerList;
    }

    /** 
    * @Description:  通过proId删除Provider
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public int deleteProviderById(Connection connection, String delId) throws Exception {
        PreparedStatement pstm = null;
        int flag = 0;
        if(null != connection){
            String sql = "delete from smbms_provider where id=?";
            Object[] params = {delId};
            flag = BaseDao.update(connection, pstm, sql, params);
            BaseDao.closeResource(connection, null, pstm);
        }
        return flag;
    }

    /** 
    * @Description: 通过proId获取Provider 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public provider getProviderById(Connection connection, String id) throws Exception {
        provider provider = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        if(null != connection){
            String sql = "select * from smbms_provider where id=?";
            Object[] params = {id};
            rs = BaseDao.execute(connection, rs, pstm, sql, params);
            if(rs.next()){
                provider = new provider();
                provider.setId(rs.getInt("id"));
                provider.setProCode(rs.getString("proCode"));
                provider.setProName(rs.getString("proName"));
                provider.setProDesc(rs.getString("proDesc"));
                provider.setProContact(rs.getString("proContact"));
                provider.setProPhone(rs.getString("proPhone"));
                provider.setProAddress(rs.getString("proAddress"));
                provider.setProFax(rs.getString("proFax"));
                provider.setCreatedBy(rs.getInt("createdBy"));
                provider.setCreationDate(rs.getTimestamp("creationDate"));
                provider.setModifyBy(rs.getInt("modifyBy"));
                provider.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            BaseDao.closeResource(connection, rs, pstm);
        }
        return provider;
    }

    /** 
    * @Description: 修改用户信息 
    * @Param:  * @param null 
    * @return:  
    * @Author: hjx
    * @Date: 2021/3/22 
    */
    @Override
    public int modify(Connection connection, provider provider) throws Exception {
        int flag = 0;
        PreparedStatement pstm = null;
        if(null != connection){
            String sql = "update smbms_provider set proName=?,proDesc=?,proContact=?," +
                    "proPhone=?,proAddress=?,proFax=?,modifyBy=?,modifyDate=? where id = ? ";
            Object[] params = {provider.getProName(),provider.getProDesc(),provider.getProContact(),provider.getProPhone(),provider.getProAddress(),
                    provider.getProFax(),provider.getModifyBy(),provider.getModifyDate(),provider.getId()};
            flag = BaseDao.update(connection, pstm, sql, params);
            BaseDao.closeResource(connection, null, pstm);
        }
        return flag;
    }
}
