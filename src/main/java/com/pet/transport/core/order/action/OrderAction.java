package com.pet.transport.core.order.action;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.order.service.IOrderService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping("/sumbitOrder")
    @ResponseBody
    public String sumbitOrder(HttpServletRequest request, HttpServletResponse response){
        String ret = "";
        Map map = new HashMap();


        String startPlaceCode = (String) request.getParameter("startPlaceCode");
        String startPlaceName = (String) request.getParameter("startPlaceName");

        String destinationPlaceCode = (String)request.getParameter("destinationPlaceCode");
        String distinationPlaceName = (String) request.getParameter("distinationPlaceName");

        String transDate = (String)request.getParameter("transDate");
        String petWeight = (String)request.getParameter("petWeight");

        //票价需要从后台生成 不能依赖前台传递的值
        String ticketPrice = "12";//(String)request.getParameter("ticketPrice");
        //航空箱自动匹配 需要判断 如果为空 根据体重生成
        String petBoxTypeName = (String)request.getParameter("petBoxTypeName");
        //航空箱价格需要从后台生成
        String petBoxPrice = (String)request.getParameter("petBoxPrice");
        //地址
        String placeAreaCode = (String)request.getParameter("placeAreaCode");
        String placeAreaName = (String)request.getParameter("placeAreaName");
        //地址价格需要从后台生成
        String placePrice = (String)request.getParameter("placePrice");
        //获取当前登录人 判空 如果为空 从shrio中获取

        String userId = (String)request.getParameter("userId");
        if(userId == null || "".equals(userId)){
            userId = (String) SecurityUtils.getSubject().getPrincipal();
            //User user = userService.selectUserById(token.getUsername());
        }
        String userMobile = (String)request.getParameter("userMobile");
        //保价 从前台传递
        String insuredPrice = (String)request.getParameter("insuredPrice");
        //总价 需要传递
        String totalPrice = (String)request.getParameter("totalPrice");
        //orderStatus 三种 草稿draft 已提交sumbit 已支付pay 已完成complate
        String orderStatus = "sumbit";//(String)request.getParameter("orderStatus");
        String payStatus = "0";//(String)request.getParameter("payStatus");
        String paySerialNo = "-1";//(String)request.getParameter("paySerialNo");
        String payAccount = "-1";//(String)request.getParameter("payAccount");

        Map param = new HashMap();
        param.put("startPlaceCode",startPlaceCode);
        param.put("startPlaceName",startPlaceName);
        param.put("destinationPlaceCode",destinationPlaceCode);
        param.put("distinationPlaceName",distinationPlaceName);
        param.put("transDate",transDate);
        param.put("petWeight",petWeight);
        param.put("ticketPrice",ticketPrice);
        param.put("petBoxTypeName",petBoxTypeName);
        param.put("petBoxPrice",petBoxPrice);
        param.put("placeAreaCode",placeAreaCode);
        param.put("placeAreaName",placeAreaName);
        param.put("placePrice",placePrice);
        param.put("userId",userId);
        param.put("userMobile",userMobile);
        param.put("insuredPrice",insuredPrice);
        param.put("totalPrice",totalPrice);
        param.put("orderStatus",orderStatus);
        param.put("payStatus",payStatus);
        param.put("paySerialNo",paySerialNo);
        param.put("payAccount",payAccount);

        int i = orderService.sumbitOrder(param);

        map.put("status","success");
        if(map!=null){
            ret =  DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }
}