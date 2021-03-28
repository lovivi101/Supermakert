package com.hong.service.provider;

import com.hong.pojo.provider;

import java.util.List;

public interface provider_service {
    public boolean add(provider provider);
    public List<provider> getProviderList(String proName, String proCode);
    public int deleteProviderById(String delId);
    public provider getProviderById(String id);
    public boolean modify(provider provider);
}
