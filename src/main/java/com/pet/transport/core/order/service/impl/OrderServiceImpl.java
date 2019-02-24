package com.pet.transport.core.order.service.impl;

import com.pet.transport.common.util.SnowflakeIdWorker;
import com.pet.transport.core.order.dao.OrderDao;
import com.pet.transport.core.order.dao.OrderPersonDao;
import com.pet.transport.core.order.dao.OrderPetDao;
import com.pet.transport.core.order.dao.OrderTicketDao;
import com.pet.transport.core.order.po.Order;
import com.pet.transport.core.order.po.OrderPerson;
import com.pet.transport.core.order.po.OrderPet;
import com.pet.transport.core.order.po.OrderTicket;
import com.pet.transport.core.order.service.IOrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderServiceImpl")
public class OrderServiceImpl implements IOrderService {

    private Log logger = LogFactory.getLog(OrderServiceImpl.class);

    @Autowired
    private  OrderDao orderDao;

    @Autowired
    private OrderPetDao orderPetDao;

    @Autowired
    private OrderPersonDao orderPersonDao;

    @Autowired
    private OrderTicketDao orderTicketDao;


    public Order selectOrderById(String id){
        return orderDao.selectOrderById(id);
    }

    public Order selectOrderByOrderNo(String orderNo){
        return orderDao.selectOrderByOrderNo(orderNo);
    }
    @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,timeout=1,isolation=Isolation.DEFAULT)
    public Map sumbitOrder(Map param) {

        String id = (String) param.get("id");
        String orderNo = (String) param.get("orderNo");
        boolean isUpdate =false;
        if(id ==null || "".equals(id)){
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            id = String.valueOf(idWorker.nextId());
            param.put("id",id);
            if(orderNo ==null || "".equals(orderNo)){
                orderNo = String.valueOf(idWorker.nextId());
                param.put("orderNo",orderNo);
            }
        }else{
            isUpdate =true;
        }

        List<Map> petLst =  (List<Map>)param.get("petLst");
        param.remove("petLst");



        //删除订单下的所有的宠物
        //Map delMap = new HashMap();
        //delMap.put("orderId",id);
        //orderPetDao.deletePetsByOrderId(delMap);
        Map<String,String> pets = new HashMap<String,String>();
        List<OrderPet> nowPetLst =  orderPetDao.selectOrderPetByOrderId(id);
        for (OrderPet pet:nowPetLst) {
            pets.put(pet.getId(),pet.getId());
        }
        List<Map> insertPetLst =  new ArrayList<Map>();
        List<Map> updatePetLst =  new ArrayList<Map>();
        List<Map> deletePetLst =  new ArrayList<Map>();
        for (Map map:petLst) {
            if(pets.containsKey(map.get("petId"))){
                //String petId = String.valueOf(idWorker.nextId());
                map.put("id",map.get("petId"));
                map.put("orderId",id);
                map.put("orderNo",orderNo);
                updatePetLst.add(map);
                pets.remove(map.get("petId"));
            }else{
                SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
                String petId = String.valueOf(idWorker.nextId());
                map.put("id",petId);
                map.put("orderId",id);
                map.put("orderNo",orderNo);
                insertPetLst.add(map);
            }

        }
        int ii=0;
        int ui=0;
        int i = 0;

        if(isUpdate){
            i = orderDao.updateOrderById(param);
        }else{
            i = orderDao.addOrder(param);
        }
        //增加
        if(!insertPetLst.isEmpty()){
            Map map =new HashMap();
            map.put("petLst",insertPetLst);
            ii= orderPetDao.saveBatchOrderPet(map);
        }

        //修改
        if(!updatePetLst.isEmpty()){
            Map map1 =new HashMap();
            map1.put("petLst",updatePetLst);
            ui= orderPetDao.batchUpdateOrderPet(map1);
        }
        //删除
        if(!pets.isEmpty()){
            for (String petId:pets.keySet()) {
                Map map =new HashMap();
                map.put("id",petId);
                deletePetLst.add(map);
            }
            if(!deletePetLst.isEmpty()){
                Map map =new HashMap();
                map.put("petLst",deletePetLst);
                orderPetDao.batchDeleteOrderPet(map);
            }
        }
        param.put("resultStatus",i);
        param.put("resultPetStatus",ii+ui);
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

    public Map sumbitPerson(Map param) {
        String id = (String) param.get("id");
        if(id ==null || "".equals(id)){
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            id = String.valueOf(idWorker.nextId());
            param.put("id",id);
        }
        int i=orderPersonDao.addOrderPerson(param);
        //更新主表状态为已提交
        Map newMap = new HashMap();
        newMap.put("id",param.get("orderId"));
        newMap.put("orderStatus","sumbit");
        newMap.put("payStatus","0");
        updateOrderStatus(newMap);
        param.put("resultStatus",i);
        return param;
    }

    public List<OrderPet> selectOrderPetByOrderId(String orderId) {
        return orderPetDao.selectOrderPetByOrderId(orderId);
    }

    public void updateOrderPersonConsignee(String orderId, String userId) {
        Map newMap = new HashMap();
        newMap.put("orderId",orderId);
        newMap.put("userId",userId);
        orderPersonDao.updateOrderPersonConsignee(newMap);
    }

    public OrderPerson selectOrderPersonByOrderId(String orderId) {
        return orderPersonDao.selectOrderPersonByOrderId(orderId);
    }

    public OrderTicket selectOrderTicketByOrderId(String orderId) {
        return orderTicketDao.selectOrderTicketByOrderId(orderId);
    }

    public Map sumbitAdminTicket(Map param) {
        String id = (String) param.get("orderTicketId");
        boolean isUpdate =false;
        if(id ==null || "".equals(id)){
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
            id = String.valueOf(idWorker.nextId());
            param.put("id",id);
        }else{
            param.put("id",id);
            isUpdate =true;
        }
        int i=0;
        if(isUpdate){
            i=orderTicketDao.updateOrderTicket(param);
        }else{
            i=orderTicketDao.addOrderTicket(param);
        }
        //更新主表状态为已提交
        Map newMap = new HashMap();
        newMap.put("id",param.get("orderId"));
        newMap.put("orderStatus","complate");
        updateOrderStatus(newMap);
        param.put("resultStatus",i);
        return param;
    }

    public List<Map> selectOrderByParm(Map param) {
        return orderDao.selectOrderByStatus(param);
    }
}
