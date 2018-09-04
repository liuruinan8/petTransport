package com.pet.transport.core.order.service;

import com.pet.transport.core.order.po.Order;

import java.util.List;
import java.util.Map;

public interface IOrderService {

   public Order selectOrderById(String id);

   public Order selectOrderByOrderNo(String orderNo);

   public int sumbitOrder(Map param);

   public int updateOrderStatus(Map param);

   public List<Map> selectOrderByParm( Map param );

   public List<Map> selectOrderByStatus(String uid, List<String> statusLst,int start,int limit);
}
