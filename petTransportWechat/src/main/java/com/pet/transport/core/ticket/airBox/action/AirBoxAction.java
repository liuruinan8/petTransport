package com.pet.transport.core.ticket.airBox.action;

import com.pet.transport.core.ticket.airBox.service.IAirBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zimok
 * @Title: AirBoxAction
 * @ProjectName petTransport
 * @Description: TODO
 * @date 2019/2/149:01
 */
@Controller
@RequestMapping("/ticket/airBox")
public class AirBoxAction {
    @Autowired
    @Qualifier("airBoxServiceImpl")
    private IAirBoxService airBoxService;

    @RequestMapping(value="/getAirBoxAll",produces={"text/html;charset=UTF-8;"})
    @ResponseBody
    public String getAllAirBoxJsonData(HttpServletRequest request, HttpServletResponse response){
        Map paramMap = new HashMap();
        String json = airBoxService.selectAllAirBoxListJsonData(paramMap);
        return json;
    }
}
