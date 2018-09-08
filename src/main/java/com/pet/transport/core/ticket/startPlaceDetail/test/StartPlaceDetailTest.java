package com.pet.transport.core.ticket.startPlaceDetail.test;

import com.pet.transport.common.test.SpringTestCase;
import com.pet.transport.core.ticket.startPlaceDetail.service.IStartPlaceDetailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

public class StartPlaceDetailTest extends SpringTestCase {
    @Autowired
    @Qualifier("startPlaceDetailServiceImpl")
    private IStartPlaceDetailService startPlaceDetailService;

    @Test
    public void selectAllDetailStartPlace(){
        Map paramMap = new HashMap();
        paramMap.put("startPlaceCode","5028_CKG");
        /*List<StartPlaceDetail> lst = startPlaceDetailService.selectAllDetailStartPlace(paramMap);
        for (StartPlaceDetail st:lst) {
            System.out.println("详细行政区划编码："+st.getDetailCode()+" 详细行政区划名称："+st.getDetailName());
        }*/
        String json = startPlaceDetailService.selectAllDetailStartPlaceToJson(paramMap);
        System.out.println(json);
    }
}
