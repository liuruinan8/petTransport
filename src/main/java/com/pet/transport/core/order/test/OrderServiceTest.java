package com.pet.transport.core.order.test;

import com.pet.transport.common.test.SpringTestCase;
import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.order.po.Order;
import com.pet.transport.core.order.service.IOrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderServiceTest extends SpringTestCase {

    @Autowired
    @Qualifier("orderServiceImpl")
    private IOrderService orderService;

    @Test
    public void selectUserByIdTest(){
        Order order = orderService.selectOrderById("1");
        System.out.println(order.getOrderNo() + ":" + order.getStartPlaceCode());
    }
    @Test
    public void selectOrderByOrderNoTest(){
        Order order = orderService.selectOrderByOrderNo("2");
        System.out.println(order.getOrderNo() + ":" + order.getStartPlaceCode());
    }
    @Test
    public void selectOrderByStatus(){
        List<String> statusLst = new ArrayList<String>();
        statusLst.add("draft");
        statusLst.add("sumbit");
        List<Map> orderLst = orderService.selectOrderByStatus("test1", statusLst,0,2);
        String ret = DataConvertUtil.convertBeanToJson(orderLst);
        System.out.println(ret);
    }
}