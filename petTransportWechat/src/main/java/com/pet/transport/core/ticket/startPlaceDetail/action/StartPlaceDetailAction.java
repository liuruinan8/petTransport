package com.pet.transport.core.ticket.startPlaceDetail.action;

import com.pet.transport.core.ticket.startPlaceDetail.service.IStartPlaceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/ticket/startPlaceDetail")
@Controller
public class StartPlaceDetailAction {

    @Autowired
    @Qualifier("startPlaceDetailServiceImpl")
    private IStartPlaceDetailService startPlaceDetailService;

    @RequestMapping(value = "/getAllDetail",produces={"text/html;charset=UTF-8;"})
    @ResponseBody
    public String getAllPlaceDetailByStartPlaceCode(HttpServletRequest request){
        String startPlaceCode = request.getParameter("startPlaceCode");
        Map paramMap = new HashMap();
        paramMap.put("startPlaceCode",startPlaceCode);
        String json = startPlaceDetailService.selectAllDetailStartPlaceToJson(paramMap);
        return json;
    }
}
