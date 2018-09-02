package com.pet.transport.core.order.service.impl;

import com.pet.transport.core.order.dao.OrderDao;
import com.pet.transport.core.order.po.Order;
import com.pet.transport.core.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("orderServiceImpl")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private  OrderDao orderDao;

    public Order selectUserById(String id){

        return orderDao.selectOrderById(id);
    }

    public Order selectOrderByOrderNo(String orderNo){

        return orderDao.selectOrderByOrderNo(orderNo);
    }
}
