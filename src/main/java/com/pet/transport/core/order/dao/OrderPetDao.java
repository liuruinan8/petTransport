package com.pet.transport.core.order.dao;

import com.pet.transport.core.order.po.OrderPet;

import java.util.List;
import java.util.Map;

/**
 * @author zimok
 * @Title: OrderPetDao
 * @ProjectName petTransport
 * @Description: 订单涉及宠物相关数据库接口层
 * @date 2019/1/1119:38
 */
public interface OrderPetDao {
    int saveBatchOrderPet(Map map);

    void deletePetsByOrderId(Map delMap);

    List<OrderPet> selectOrderPetByOrderId(String orderId);

    int batchUpdateOrderPet(Map map);

    void batchDeleteOrderPet(Map map);
}
