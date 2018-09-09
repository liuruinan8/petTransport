package com.pet.transport.core.ticket.airBox.test;

import com.pet.transport.common.test.SpringTestCase;
import com.pet.transport.core.ticket.airBox.po.AirBox;
import com.pet.transport.core.ticket.airBox.service.IAirBoxService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirBoxServiceTest extends SpringTestCase {
    @Autowired
    @Qualifier("airBoxServiceImpl")
    private IAirBoxService service;

    @Test
    public void getAirBoxListByFeature(){
        Map map = new HashMap();
        map.put("weight",10);
        List<AirBox> lst = service.selectAirBoxListByFeature(map);
        if (lst!=null && lst.size()>0){
            System.out.println("Find");
            for (AirBox airBox:lst) {
                System.out.println(airBox.getBoxTypeId()+airBox.getBoxTypeName());
            }
        }else{
            System.out.println("Not Find");
        }

    }
}
