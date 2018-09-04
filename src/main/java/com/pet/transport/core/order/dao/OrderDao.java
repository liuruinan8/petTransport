package com.pet.transport.core.order.dao;

import com.pet.transport.core.order.po.Order;

import java.util.Map;

public interface OrderDao {

    public Order selectOrderById(String id);

    public Order selectOrderByOrderNo(String orderNo);

    public int addOrder(Map map);

}
