package com.hong.dao.provider;

import com.hong.pojo.provider;

import java.sql.Connection;
import java.util.List;

public interface provider_dao {
    public int add(Connection connection, provider provider)throws Exception;
    public List<provider> getProviderList(Connection connection, String proName, String proCode)throws Exception;
    public int deleteProviderById(Connection connection, String delId)throws Exception;
    public provider getProviderById(Connection connection, String id)throws Exception;
    public int modify(Connection connection, provider provider)throws Exception;
}
