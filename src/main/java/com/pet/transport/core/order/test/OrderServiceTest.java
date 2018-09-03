package com.pet.transport.core.order.test;

import com.pet.transport.common.test.SpringTestCase;
import com.pet.transport.core.order.po.Order;
import com.pet.transport.core.order.service.IOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class OrderServiceTest extends SpringTestCase {

    @Autowired
    @Qualifier("orderServiceImpl")
    private IOrderService orderService;

    @Test
    public void selectUserByIdTest(){
        Order order = orderService.selectOrderById("1");
        System.out.println(order.getOrderNo() + ":" + order.getStartPlaceId());
    }
    @Test
    public void selectOrderByOrderNoTest(){
        Order order = orderService.selectOrderByOrderNo("2");
        System.out.println(order.getOrderNo() + ":" + order.getStartPlaceId());
    }

}