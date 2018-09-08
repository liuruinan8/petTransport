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
        String transDate = (String)request.getParameter("transDate");
        String petKind = (String)request.getParameter("petKind");
        String petWeight = (String)request.getParameter("petWeight");
        String selHkx = (String) request.getParameter("selHkx");
        String selSmjc = (String) request.getParameter("selSmjc");
        String placeAreaCode = (String)request.getParameter("placeAreaCode");
        //保价 从前台传递
        String insuredPrice = (String)request.getParameter("insuredPrice");

        Map costParam = new HashMap();
        costParam.put("selHkx",selHkx);
        costParam.put("selSmjc",selSmjc);
        costParam.put("transDate",transDate);
        costParam.put("placeAreaCode",placeAreaCode);
        costParam.put("petKind",petKind);
        costParam.put("petWeight",petWeight);
        costParam.put("startPlaceCode",startPlaceCode);
        costParam.put("destinationPlaceCode",destinationPlaceCode);
        costParam.put("insuredPrice",insuredPrice);

        Map map = ticketPriceServiceImpl.getAllCost(costParam);
        if(map!=null){
            ret = DataConvertUtil.convertMapToJson(map);
        }
        return ret;
    }
}
