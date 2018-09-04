package com.pet.transport.core.ticket.price.service.impl;


import com.pet.transport.core.ticket.price.service.ITicketPriceService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("ticketPriceServiceImpl")
public class TicketPriceServiceImpl implements ITicketPriceService {


    public Map getAllCost(Map param) {






        String ticketPrice = "130";
        String petBoxPrice = "140";
        String placePrice = "150";
        String insuredPrice = "160";
        String totalPrice = "580";
        Map map = new HashMap();
        map.put("ticketPrice",ticketPrice);
        map.put("petBoxPrice",petBoxPrice);
        map.put("placePrice",placePrice);
        map.put("insuredPrice",insuredPrice);
        map.put("totalPrice",totalPrice);
        return map;
    }
}
