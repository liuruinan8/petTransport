package com.pet.transport.core.order.service;

import com.pet.transport.core.order.po.Order;

import java.util.Map;

public interface IOrderService {

   public Order selectOrderById(String id);

   public Order selectOrderByOrderNo(String orderNo);

   public int sumbitOrder(Map param);
}
