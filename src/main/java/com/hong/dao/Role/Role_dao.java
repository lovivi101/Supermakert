package com.hong.dao.Role;

import com.hong.pojo.role;

import java.sql.Connection;
import java.util.List;

public interface Role_dao {
    //获取角色列表
    public List<role> getRoleList(Connection connection)throws Exception;
}
