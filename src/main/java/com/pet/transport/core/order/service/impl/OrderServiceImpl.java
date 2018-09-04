package com.pet.transport.core.order.service.impl;

import com.pet.transport.common.util.SnowflakeIdWorker;
import com.pet.transport.core.order.dao.OrderDao;
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

    public Order selectOrderById(String id){

        return orderDao.selectOrderById(id);
    }

    public Order selectOrderByOrderNo(String orderNo){

        return orderDao.selectOrderByOrderNo(orderNo);
    }

    public int sumbitOrder(Map param) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);

        String id = (String) param.get("id");
        if(id ==null || "".equals(id)){
            id = String.valueOf(idWorker.nextId());
            param.put("id",id);
        }
        String orderNo = (String) param.get("orderNo");
        if(orderNo ==null || "".equals(orderNo)){
            orderNo = id;
            param.put("orderNo",orderNo);
        }

        /*String startPlaceCode = (String) param.get("startPlaceCode");
        String startPlaceName = (String) param.get("startPlaceName");

        String destinationPlaceCode = (String)param.get("destinationPlaceCode");
        String distinationPlaceName = (String) param.get("distinationPlaceName");

        String transDate = (String)param.get("transDate");
        String petWeight = (String)param.get("petWeight");

        String ticketPrice = (String)param.get("ticketPrice");

        String petBoxTypeName = (String)param.get("petBoxTypeName");
        String petBoxPrice = (String)param.get("petBoxPrice");

        String placeAreaCode = (String)param.get("placeAreaCode");
        String placeAreaName = (String)param.get("placeAreaName");
        String placePrice = (String)param.get("placePrice");

        String userId = (String)param.get("userId");
        String userMobile = (String)param.get("userMobile");

        String insuredPrice = (String)param.get("insuredPrice");

        String totalPrice = (String)param.get("totalPrice");
        String orderStatus = (String)param.get("orderStatus");
        String payStatus = (String)param.get("payStatus");
        String paySerialNo = (String)param.get("paySerialNo");
        String payAccount = (String)param.get("payAccount");*/


        //组装Order
        return orderDao.addOrder(param);
    }

    public int updateOrderStatus(Map param) {
        return 0;
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
