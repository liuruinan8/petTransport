package com.pet.transport.core.ticket.price.action;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.ticket.price.service.ITicketPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/ticket/price")
@Controller
public class TicektPriceAction {
    @Autowired
    @Qualifier("ticketPriceServiceImpl")
    ITicketPriceService ticketPriceServiceImpl;

    @RequestMapping(value = "/countCost",produces={"text/html;charset=UTF-8;"})
    @ResponseBody
    public String countCost(HttpServletRequest request, HttpServletResponse response){
        String ret="";
        String startPlaceCode = request.getParameter("startPlaceCode");
        String destinationPlaceCode = request.getParameter("destinationPlaceCode");
        String selectDate = request.getParameter("selectDate");
        String petWeight = request.getParameter("petWeight");
        String selHkx = request.getParameter("selHkx");
        Map param = new HashMap();
        param.put("startPlaceCode",startPlaceCode);
        param.put("destinationPlaceCode",destinationPlaceCode);
        param.put("selectDate",selectDate);
        param.put("petWeight",petWeight);
        param.put("selHkx",selHkx);

        Map map = ticketPriceServiceImpl.getAllCost(param);
        if(map!=null){
            ret = DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }
}
