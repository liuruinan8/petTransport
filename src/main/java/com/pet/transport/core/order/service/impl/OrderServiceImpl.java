package com.pet.transport.core.order.service.impl;

import com.pet.transport.common.util.SnowflakeIdWorker;
import com.pet.transport.core.order.dao.OrderDao;
import com.pet.transport.core.order.dao.OrderPetDao;
import com.pet.transport.core.order.po.Order;
import com.pet.transport.core.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderServiceImpl")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private  OrderDao orderDao;

    @Autowired
    private OrderPetDao orderPetDao;

    public Order selectOrderById(String id){

        return orderDao.selectOrderById(id);
    }

    public Order selectOrderByOrderNo(String orderNo){

        return orderDao.selectOrderByOrderNo(orderNo);
    }

    public Map sumbitOrder(Map param) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

        String id = (String) param.get("id");
        if(id ==null || "".equals(id)){
            id = String.valueOf(idWorker.nextId());
            param.put("id",id);
        }
        String orderNo = (String) param.get("orderNo");
        if(orderNo ==null || "".equals(orderNo)){
            orderNo = String.valueOf(idWorker.nextId());
            param.put("orderNo",orderNo);
        }
        List<Map> petLst =  (List<Map>)param.get("petLst");
        param.remove("petLst");
        int i=orderDao.addOrder(param);
        //删除订单下的所有的宠物
        Map delMap = new HashMap();
        delMap.put("orderId",id);
        orderPetDao.deletePetsByOrderId(delMap);

        for (Map map:petLst) {
            String petId = String.valueOf(idWorker.nextId());
            map.put("id",petId);
            map.put("orderId",id);
            map.put("orderNo",orderNo);
        }
        //重新增加
        orderPetDao.saveBatchOrderPet(petLst);
        param.put("resultStatus",i);
        //组装Order
        return param;
    }

    public Map updateOrderStatus(Map param) {
        int i=orderDao.updateOrderStatus(param);
        param.put("resultStatus",i);
        //组装Order
        return param;
    }

    public List<Map> selectOrderByStatus(String uid, List<String> statusLst,int start,int limit) {
        Map param = new HashMap();
        param.put("userId",uid);
        param.put("orderStatusLst",statusLst);
        param.put("start",start);
        param.put("limit",limit);
        return selectOrderByParm(param);
    }
    public List<Map> selectOrderByParm( Map param ) {
        return orderDao.selectOrderByStatus(param);
    }
}
