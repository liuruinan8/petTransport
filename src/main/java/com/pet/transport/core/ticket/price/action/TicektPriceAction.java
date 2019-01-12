package com.pet.transport.core.ticket.price.action;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.ticket.price.service.ITicketPriceService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
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
        String petstr = request.getParameter("pets");
        JSONArray pets = JSONArray.fromObject(petstr);
        List<Map> petLst=(List)JSONArray.toCollection(pets,Map.class);
        String startPlaceCode = request.getParameter("startPlaceCode");
        String destinationPlaceCode = request.getParameter("destinationPlaceCode");
        String transDate = (String)request.getParameter("transDate");
        String petKind = (String)request.getParameter("petKind");
        String petWeight = (String)request.getParameter("petWeight");
        String selHkx = (String) request.getParameter("selHkx");
        String selSmjc = (String) request.getParameter("selSmjc");
        String placeAreaCode = (String)request.getParameter("placeAreaCode");
        //保价 从前台传递
        //保价 从前台传递
        String selBjfw=(String) request.getParameter("selBjfw");
        String insuredPrice = "0";// (String)request.getParameter("insuredPrice");
        if(selBjfw!=null && (selBjfw.equals("true")||selBjfw.equals("on"))){
            //默认保价为200元
            insuredPrice = "200";
        }
        Map costParam = new HashMap();
        costParam.put("petLst",petLst);
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
