package com.hong.service.bill;

import com.hong.pojo.Bill;

import java.util.List;

public interface bill_service {
    public boolean add(Bill bill);
    public List<Bill> getBillList(Bill bill);
    public boolean deleteBillById(String delId);
    public Bill getBillById(String id);
    public boolean modify(Bill bill);

}
