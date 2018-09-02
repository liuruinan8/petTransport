package com.pet.transport.core.order.dao;

import com.pet.transport.core.order.po.Order;

public interface OrderDao {

    public Order selectOrderById(String id);

    public Order selectOrderByOrderNo(String orderNo);

}
