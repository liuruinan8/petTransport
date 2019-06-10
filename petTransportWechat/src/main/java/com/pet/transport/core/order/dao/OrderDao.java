package com.pet.transport.core.order.dao;

import com.pet.transport.core.order.po.Order;

import java.util.List;
import java.util.Map;

public interface OrderDao {

    public Order selectOrderById(String id);

    public Order selectOrderByOrderNo(String orderNo);

    public List<Map> selectOrderByStatus(Map param);

    public int addOrder(Map map);

    public int updateOrderById(Map map);

    public int updateOrderStatus(Map param);
}
