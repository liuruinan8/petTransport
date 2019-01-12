package com.pet.transport.core.order.service;

import com.pet.transport.core.order.po.Order;

import java.util.List;
import java.util.Map;

public interface IOrderService {

   public Order selectOrderById(String id);

   public Order selectOrderByOrderNo(String orderNo);

   public Map sumbitOrder(Map param);

   public Map updateOrderStatus(Map param);

   List<Map> selectOrderByParm( Map param );

   List<Map> selectOrderByStatus(String uid, List<String> statusLst,int start,int limit);

   Map sumbitPerson(Map param);
}
