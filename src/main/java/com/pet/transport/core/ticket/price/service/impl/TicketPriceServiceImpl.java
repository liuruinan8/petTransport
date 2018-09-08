package com.pet.transport.core.ticket.price.service.impl;


import com.pet.transport.core.ticket.airBox.po.AirBox;
import com.pet.transport.core.ticket.airBox.service.IAirBoxService;
import com.pet.transport.core.ticket.destinationPlace.po.DestinationPlace;
import com.pet.transport.core.ticket.destinationPlace.service.IDestinationPlaceService;
import com.pet.transport.core.ticket.price.service.ITicketPriceService;
import com.pet.transport.core.ticket.startPlaceDetail.po.StartPlaceDetail;
import com.pet.transport.core.ticket.startPlaceDetail.service.IStartPlaceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("ticketPriceServiceImpl")
public class TicketPriceServiceImpl implements ITicketPriceService {

    @Autowired
    @Qualifier("destinationPlaceServiceImpl")
    private IDestinationPlaceService destinationPlaceService;
    @Autowired
    @Qualifier("airBoxServiceImpl")
    private IAirBoxService airBoxService;

    @Autowired
    @Qualifier("startPlaceDetailServiceImpl")
    private IStartPlaceDetailService startPlaceDetailService;
    public Map getAllCost(Map param) {
        //String startPlaceCode = (String) param.get("startPlaceCode");
        //String destinationPlaceCode = (String) param.get("destinationPlaceCode");
        //String transDate = (String) param.get("transDate");
        String petWeight = (String) param.get("petWeight");
        String selHkx = (String) param.get("selHkx");
        String selSmjc = (String) param.get("selSmjc");
        String placeAreaCode = (String) param.get("placeAreaCode");
        String insuredPrice = (String) param.get("insuredPrice");
        if(insuredPrice == null || "".equals(insuredPrice)){
            insuredPrice = "0";
        }
        //计算机票价格
        String ticketPrice = "0";
        DestinationPlace destinationPlace=destinationPlaceService.selectDestinationPlaceByStartPlaceAndDistPlace(param);
        if(destinationPlace!=null){
            ticketPrice =destinationPlace.getPrice();
        }
        String petBoxPrice = "0";
        String boxTypeId = "";
        String boxTypeName ="";
        if(selHkx!=null && selHkx.equals("true")){
            //计算航空箱价格
            Map airParaMmap = new HashMap();
            airParaMmap.put("weight",Integer.parseInt(petWeight));
            List<AirBox> lst = airBoxService.selectAirBoxListByFeature(airParaMmap);
            if(lst != null && lst.size()>0){
                AirBox airBox = lst.get(0);
                if(airBox != null ){
                    petBoxPrice = airBox.getPrice();
                    boxTypeId = airBox.getBoxTypeId();
                    boxTypeName = airBox.getBoxTypeName();
                }
            }

        }
        String placePrice = "0";
        if(selSmjc!=null && selSmjc.equals("true")){
            Map placeMap = new HashMap();
            placeMap.put("detailCode",placeAreaCode);
            StartPlaceDetail dt = startPlaceDetailService.selectDetailByDetailCode(placeMap);
            if(dt != null){
                placePrice = dt.getPrice();
            }
        }

        Double totalPrice = Double.parseDouble(ticketPrice) +
                Double.parseDouble(petBoxPrice) +
                Double.parseDouble(placePrice) +
                Double.parseDouble(insuredPrice) ;
        Map map = new HashMap();
        map.put("ticketPrice",ticketPrice);
        map.put("petBoxPrice",petBoxPrice);
        map.put("boxTypeId",boxTypeId);
        map.put("boxTypeName",boxTypeName);
        map.put("placePrice",placePrice);
        map.put("insuredPrice",insuredPrice);
        map.put("totalPrice",totalPrice.toString());
        return map;
    }
}
