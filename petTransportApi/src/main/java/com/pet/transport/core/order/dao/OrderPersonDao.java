package com.pet.transport.core.order.dao;

import com.pet.transport.core.order.po.OrderPerson;

import java.util.Map;

/**
 * @author zimok
 * @Title: OrderPerson
 * @ProjectName petTransport
 * @Description: 订单相关人员相关数据库接口层
 * @date 2019/1/1119:37
 */
public interface OrderPersonDao {
    public  int addOrderPerson(Map param);

    void updateOrderPersonConsignee(Map param);

    OrderPerson selectOrderPersonByOrderId(String orderId);
}
