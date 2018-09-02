package com.pet.transport.core.ticket.destinationPlace.action;


import com.pet.transport.core.ticket.destinationPlace.service.IDestinationPlaceService;
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
@RequestMapping("/ticket/startPlace")
public class DestinationPlaceAction {
    @Autowired
    @Qualifier("destinationPlaceServiceImpl")
    private IDestinationPlaceService destinationPlaceService;

    //produces只有第一个是有效的？
    @RequestMapping(value="/getAll",produces={"text/html;charset=UTF-8;"})
    @ResponseBody
    public String getAllStartPlaceJsonData(HttpServletRequest request, HttpServletResponse response){
        Map paramMap = new HashMap();
        String startPlaceCode = request.getParameter("startPlaceCode");
        paramMap.put("START_PLACE_CODE",startPlaceCode);
        paramMap.put("orderfield","destination_PLACE_SIMPLE");
        paramMap.put("orderdir","DESC");
        String json = destinationPlaceService.selectDestinationPlaceByStartPlaceJson(paramMap);
        return json;
    }
}
