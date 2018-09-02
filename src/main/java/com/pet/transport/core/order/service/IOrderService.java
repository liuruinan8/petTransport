package com.pet.transport.core.order.service;

import com.pet.transport.core.order.po.Order;

public interface IOrderService {

   public Order selectUserById(String id);

   public Order selectOrderByOrderNo(String orderNo);
}
