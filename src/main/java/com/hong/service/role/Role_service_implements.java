package com.hong.service.role;

import com.hong.dao.BaseDao;
import com.hong.dao.Role.Role_dao;
import com.hong.dao.Role.Role_dao_implements;
import com.hong.pojo.role;

import java.sql.Connection;
import java.util.List;

/**
 * @description:
 * @author: hjx
 * @time: 2021年03月19日 19:10
 */
public class Role_service_implements implements Role_service_interface {
    @Override
    public List<role> getRoleList() {
        Connection connection = null;
        List<role> roleList = null;
        Role_dao role_dao = new Role_dao_implements();
        try {
            connection = BaseDao.getConnection();
            roleList = role_dao.getRoleList(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return roleList;
    }
}
