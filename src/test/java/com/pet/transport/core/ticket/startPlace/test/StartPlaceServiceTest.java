package com.pet.transport.core.ticket.startPlace.test;

import com.pet.transport.common.test.SpringTestCase;
import com.pet.transport.core.ticket.startPlace.service.IStartPlaceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

public class StartPlaceServiceTest extends SpringTestCase {

    @Autowired
    @Qualifier("startPlaceServiceImpl")
    private IStartPlaceService startPlaceServiceImpl;

    @Test
    public void updateStartPlace(){
        Map startPlace = new HashMap();
        startPlace.put("startPlaceCode","5028_CKG");
        startPlace.put("startPlaceName","重庆江北");
        startPlace.put("startPlaceSimple","C");
        startPlaceServiceImpl.updateStartPlace(startPlace);
        //System.out.println("update StartPlaceUer Success" );
    }
    @Test
    public void addStartPlace(){
        Map paramMap = new HashMap();
        paramMap.put("startPlaceCode","5028_CKG");
        paramMap.put("startPlaceName","重庆江北");
        paramMap.put("startPlaceSimple","C");
        startPlaceServiceImpl.addStartPlace(paramMap);
        //System.out.println("AddUer Success" );
    }
    @Test
    public void selectAllStartPlaceTest(){
        Map paramMap = new HashMap();
        //paramMap.put("groupfield","START_PLACE_SIMPLE");

        paramMap.put("orderfield","START_PLACE_SIMPLE");
        paramMap.put("orderdir","DESC");
        /**/
        String startPlaceLst = startPlaceServiceImpl.selectAllStartPlaceToJson(paramMap);
        //System.out.println(startPlaceLst);
    }
}