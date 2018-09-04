package com.pet.transport.core.order.action;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.order.service.IOrderService;
import com.pet.transport.uc.user.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/ticket/order")
public class OrderAction {
    @Autowired
    @Qualifier("orderServiceImpl")
    private IOrderService orderService;

    @RequestMapping("/mine")
    @ResponseBody
    public ModelAndView myOrder(){
        return new ModelAndView("myOrder");
    }

    private Map organizeRequest(HttpServletRequest  request){
        String startPlaceCode = (String) request.getParameter("startPlaceCode");
        String startPlaceName = (String) request.getParameter("startPlaceName");

        String destinationPlaceCode = (String)request.getParameter("destinationPlaceCode");
        String destinationPlaceName = (String) request.getParameter("destinationPlaceName");

        String transDate = (String)request.getParameter("transDate");
        String petKind = (String)request.getParameter("petKind");
        String petWeight = (String)request.getParameter("petWeight");

        //票价需要从后台生成 不能依赖前台传递的值
        String ticketPrice = "12";//(String)request.getParameter("ticketPrice");
        //航空箱自动匹配 需要判断 如果为空 根据体重生成
        String petBoxTypeId = "1";//(String)request.getParameter("petBoxTypeName");
        String petBoxTypeName = "1号航空箱";//(String)request.getParameter("petBoxTypeName");
        //航空箱价格需要从后台生成
        String petBoxPrice = "23";//(String)request.getParameter("petBoxPrice");
        //地址
        String placeAreaCode = (String)request.getParameter("placeAreaCode");
        String placeAreaName = (String)request.getParameter("placeAreaName");
        String placeDetail = (String)request.getParameter("placeDetail");
        //地址价格需要从后台生成
        String placePrice = "34";//(String)request.getParameter("placePrice");
        //获取当前登录人 判空 如果为空 从shrio中获取

        String userId = (String)request.getParameter("userId");
        String userMobile = (String)request.getParameter("userMobile");
        if(userId == null || "".equals(userId)){
            Map userMap =UserUtil.getInstance().getLoginUserMap();
            if(userMap!=null && !userMap.isEmpty()){
                if(userMap.containsKey("userId")){
                    userId = (String) userMap.get("userId");
                }
                if(userMap.containsKey("userMobile")){
                    userMobile = (String) userMap.get("userMobile");
                }
            }
        }

        //保价 从前台传递
        String insuredPrice = (String)request.getParameter("insuredPrice");
        //总价 需要传递
        String totalPrice ="123";// (String)request.getParameter("totalPrice");


        Map param = new HashMap();
        param.put("startPlaceCode",startPlaceCode);
        param.put("startPlaceName",startPlaceName);
        param.put("destinationPlaceCode",destinationPlaceCode);
        param.put("destinationPlaceName",destinationPlaceName);
        param.put("transDate",transDate);
        param.put("petWeight",petWeight);
        param.put("petKind",petKind);
        param.put("ticketPrice",ticketPrice);
        param.put("petBoxTypeId",petBoxTypeId);
        param.put("petBoxTypeName",petBoxTypeName);
        param.put("petBoxPrice",petBoxPrice);
        param.put("placeAreaCode",placeAreaCode);
        param.put("placeAreaName",placeAreaName);
        param.put("placeDetail",placeDetail);
        param.put("placePrice",placePrice);
        param.put("userId",userId);
        param.put("userMobile",userMobile);
        param.put("insuredPrice",insuredPrice);
        param.put("totalPrice",totalPrice);

        return param;
    }

    @RequestMapping("/save")
    @ResponseBody
    public String saveOrder(HttpServletRequest request, HttpServletResponse response){
        String ret = "";
        Map map = new HashMap();
        Map param =organizeRequest(request);

        //orderStatus 三种 草稿draft 已提交sumbit 已支付pay 已完成complate 线下支付 offlinePay
        String orderStatus = "draft";//(String)request.getParameter("orderStatus");
        String payStatus = "0";//(String)request.getParameter("payStatus");
        String paySerialNo = "-1";//(String)request.getParameter("paySerialNo");
        String payAccount = "-1";//(String)request.getParameter("payAccount");

        param.put("orderStatus",orderStatus);
        param.put("payStatus",payStatus);
        param.put("paySerialNo",paySerialNo);
        param.put("payAccount",payAccount);

        int i = orderService.sumbitOrder(param);
        if(i==1){
            map.put("status","success");
        }else{
            map.put("status","fail");
        }

        if(map!=null){
            ret =  DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }

    @RequestMapping("/sumbitOrder")
    @ResponseBody
    public String sumbitOrder(HttpServletRequest request, HttpServletResponse response){
        String ret = "";
        Map map = new HashMap();
        Map param =organizeRequest(request);

        //orderStatus 三种 草稿draft 已提交sumbit 已支付pay 已完成complate
        String orderStatus = "sumbit";//(String)request.getParameter("orderStatus");
        String payStatus = "0";//(String)request.getParameter("payStatus");
        String paySerialNo = "-1";//(String)request.getParameter("paySerialNo");
        String payAccount = "-1";//(String)request.getParameter("payAccount");

        param.put("orderStatus",orderStatus);
        param.put("payStatus",payStatus);
        param.put("paySerialNo",paySerialNo);
        param.put("payAccount",payAccount);

        int i = orderService.sumbitOrder(param);
        if(i==1){
            map.put("status","success");
        }else{
            map.put("status","fail");
        }
        map.put("status","success");

        if(map!=null){
            ret =  DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }

    @RequestMapping("/updateStatus/${status}")
    @ResponseBody
    public String updateStatus(HttpServletRequest request, HttpServletResponse response,@PathVariable("status") String status){
        String ret = "";
        Map map = new HashMap();
        String id = request.getParameter("id");
        Map param = new HashMap();
        param.put("id",id);
        param.put("status",param);
        int i = orderService.updateOrderStatus(param);
        if(i==1){
            map.put("status","success");
        }else{
            map.put("status","fail");
        }
        map.put("status","success");

        if(map!=null){
            ret =  DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }


}
