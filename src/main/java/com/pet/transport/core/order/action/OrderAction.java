package com.pet.transport.core.order.action;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.order.service.IOrderService;
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
        Map param = new HashMap();
        int i = orderService.sumbitOrder(param);
        map.put("status","success");
        if(map!=null){
            ret =  DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }
}
