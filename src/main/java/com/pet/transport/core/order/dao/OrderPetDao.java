package com.pet.transport.core.order.dao;

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
    public void saveBatchOrderPet(List<Map> petLst);

    public void deletePetsByOrderId(Map delMap);
}
