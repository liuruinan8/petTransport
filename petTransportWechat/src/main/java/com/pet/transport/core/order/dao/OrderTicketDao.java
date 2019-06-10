package com.pet.transport.core.order.dao;

import com.pet.transport.core.order.po.OrderTicket;

import java.util.Map;

/**
 * @author zimok
 * @Title: OrderTicketDao
 * @ProjectName petTransport
 * @Description: 订单机票航班       相关数据库接口层
 * @date 2019/1/1119:38
 */
public interface OrderTicketDao {
    OrderTicket selectOrderTicketByOrderId(String orderId);

    int addOrderTicket(Map param);

    int updateOrderTicket(Map param);
}
