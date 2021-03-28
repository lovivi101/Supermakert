package com.hong.dao.Role;

import com.hong.dao.BaseDao;
import com.hong.pojo.role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: hjx
 * @time: 2021年03月19日 19:06
 */
public class Role_dao_implements implements Role_dao{
    @Override
    public List<role> getRoleList(Connection connection) throws Exception {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<role> roleList = new ArrayList<role>();
        if (connection != null) {
            String sql = "select * from smbms_role";
            Object[] params = {};
            rs = BaseDao.execute(connection, rs, pstm, sql, params);
            while (rs.next()) {
                role _role = new role();
                _role.setId(rs.getInt("id"));
                _role.setRoleCode(rs.getString("roleCode"));
                _role.setRoleName(rs.getString("roleName"));
                roleList.add(_role);
            }
            BaseDao.closeResource(null, rs, pstm);
        }
        return roleList;
    }
}
