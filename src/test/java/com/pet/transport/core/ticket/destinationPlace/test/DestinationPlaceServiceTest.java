package com.pet.transport.core.ticket.destinationPlace.test;

import com.pet.transport.common.test.SpringTestCase;
import com.pet.transport.core.ticket.destinationPlace.po.DestinationPlace;
import com.pet.transport.core.ticket.destinationPlace.service.IDestinationPlaceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

public class DestinationPlaceServiceTest extends SpringTestCase {

    @Autowired
    @Qualifier("destinationPlaceServiceImpl")
    private IDestinationPlaceService destinationPlaceService;

    @Test
    public void updateStartPlace(){
        Map destinationPlace = new HashMap();
        destinationPlace.put("startPlaceCode","5028_CKG");
        destinationPlace.put("startPlaceName","重庆江北");
        destinationPlace.put("startPlaceSimple","C");
        destinationPlaceService.updateDestinationPlace(destinationPlace);
        System.out.println("update StartPlaceUer Success" );
    }
    @Test
    public void addStartPlace(){
        Map paramMap = new HashMap();
        paramMap.put("startPlaceCode","5028_CKG");
        paramMap.put("startPlaceName","重庆江北");
        paramMap.put("startPlaceSimple","C");
        destinationPlaceService.addDestinationPlace(paramMap);
        System.out.println("AddUer Success" );
    }
    @Test
    public void selectAllStartPlaceTest(){
        Map paramMap = new HashMap();
        //paramMap.put("groupfield","START_PLACE_SIMPLE");
        paramMap.put("startPlaceCode","5028_CKG");
        paramMap.put("orderfield","DESTINATION_PLACE_SIMPLE");
        paramMap.put("orderdir","DESC");
        /**/
        String startPlaceLst = destinationPlaceService.selectDestinationPlaceByStartPlaceJson(paramMap);
        System.out.println(startPlaceLst);
    }
    @Test
    public void selectDestinationPlaceByStartPlaceAndDistPlace(){
        Map paramMap = new HashMap();
        //paramMap.put("groupfield","START_PLACE_SIMPLE");
        paramMap.put("startPlaceCode","5028_CKG");
        paramMap.put("destinationPlaceCode","415");

        DestinationPlace destinationPlace=destinationPlaceService.selectDestinationPlaceByStartPlaceAndDistPlace(paramMap);
        if(destinationPlace!=null){
            System.out.println(destinationPlace.getPrice());
        }
        /*
        String startPlaceLst = destinationPlaceService.selectDestinationPlaceByStartPlaceJson(paramMap);
        System.out.println(startPlaceLst);*/
    }
}