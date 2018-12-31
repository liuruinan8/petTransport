package com.pet.transport.core.ticket.petKind.action;


import com.pet.transport.core.ticket.petKind.service.IPetKindService;
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
@RequestMapping("/ticket/petKind")
public class PetKindAction {
    @Autowired
    @Qualifier("petKindServiceImpl")
    private IPetKindService petKindService;

    //produces只有第一个是有效的？
    @RequestMapping(value="/getAll",produces={"text/html;charset=UTF-8;"})
    @ResponseBody
    public String getPetKindJsonData(HttpServletRequest request, HttpServletResponse response){
        Map paramMap = new HashMap();
        paramMap.put("groupfield","KIND_NAME_SIMPLE");
        //paramMap.put("orderfield","START_PLACE_SIMPLE");
        //paramMap.put("orderdir","DESC");
        String json = petKindService.selectAllPetKindToJson(paramMap);
        return json;
    }
}
