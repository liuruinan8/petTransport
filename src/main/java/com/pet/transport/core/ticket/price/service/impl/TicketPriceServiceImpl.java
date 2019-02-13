package com.pet.transport.core.ticket.price.service.impl;


import com.pet.transport.core.ticket.airBox.po.AirBox;
import com.pet.transport.core.ticket.airBox.service.IAirBoxService;
import com.pet.transport.core.ticket.destinationPlace.po.DestinationPlacePrice;
import com.pet.transport.core.ticket.destinationPlace.service.IDestinationPlaceService;
import com.pet.transport.core.ticket.price.service.ITicketPriceService;
import com.pet.transport.core.ticket.startPlaceDetail.service.IStartPlaceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        String userType = (String) param.get("userType");
        if(StringUtils.isEmpty(userType)){
            userType = "plain";
        }
        //String petWeight = (String) param.get("petWeight");
        //String selHkx = (String) param.get("selHkx");
        String selSmjc = (String) param.get("selSmjc");
        String placeDistance = (String) param.get("placeDistance");
        String insuredPrice = (String) param.get("insuredPrice");
        List<Map> petLst = (List<Map>) param.get("petLst");
        if(insuredPrice == null || "".equals(insuredPrice)){
            insuredPrice = "0";
        }
        int petNum = petLst.size();
        //计算机票价格
        String ticketPrice = "0";
        /*DestinationPlace destinationPlace=destinationPlaceService.selectDestinationPlaceByStartPlaceAndDistPlace(param);
        if(destinationPlace!=null){
            ticketPrice =destinationPlace.getPrice();
        }*/

        DestinationPlacePrice destinationPlacePrice=destinationPlaceService.selectPriceByStartPlaceAndDistPlaceAndUseType(param);
        //最低收费
        String lowPrice=destinationPlacePrice.getLowPrice();
        //增加一只收费
        String pricePerOne=destinationPlacePrice.getPricePerOne();
        //区间费率(元/kg) 收费8kg<N<=20kg
        String rateIntervalA=destinationPlacePrice.getRateIntervalA();
        //区间费率(元/kg) 收费20kg<N<=40kg
        String rateIntervalB=destinationPlacePrice.getRateIntervalB();
        //40kg<N
        String rateIntervalC=destinationPlacePrice.getRateIntervalC();
        //预留三个字段
        String rateIntervalD=destinationPlacePrice.getRateIntervalD();
        //预留三个字段
        String rateIntervalE=destinationPlacePrice.getRateIntervalE();
        //预留三个字段
        String rateIntervalF=destinationPlacePrice.getRateIntervalF();
        //单只超重（>40kg）
        String  singleOverWeightRate=destinationPlacePrice.getSingleOverWeightRate();
        //检疫证明代办费用
        String quarantineCertCost=destinationPlacePrice.getQuarantineCertCost();
        //计算价格
        int basePrice = Integer.valueOf(lowPrice)+Integer.valueOf(pricePerOne) * (petNum - 1);

        //遍历所有宠物
        //1.单个大于40KG的 每个加50元
        int petBoxPriceAll = 0;
        int overWeightCount = 0;
        int totalWeight = 0;
        int quarantineCertPrice=0;
        for (Map pet:petLst) {
            String petWeight = (String) pet.get("petWeight");
            int petWeightInt =  Integer.valueOf(petWeight);
            if(petWeightInt>40){
                overWeightCount++;
            }
            totalWeight+=petWeightInt;
            String selHkx =(String)pet.get("selHkx");
            String selDWJYHG = (String) pet.get("selDWJYHG");

            String boxTypeId = "";
            String boxTypeName ="";
            if(selHkx!=null && (selHkx.equals("true")||selHkx.equals("on"))){
                //计算航空箱价格
                Map airParaMmap = new HashMap();
                airParaMmap.put("weight",Integer.parseInt(petWeight));
                List<AirBox> lst = airBoxService.selectAirBoxListByFeature(airParaMmap);
                if(lst != null && lst.size()>0){
                    AirBox airBox = lst.get(0);
                    if(airBox != null ){
                        String singleBoxPrice = airBox.getPrice();
                        pet.put("singleBoxPrice",singleBoxPrice);
                        String petBoxTypeId = airBox.getBoxTypeId();
                        pet.put("petBoxTypeId",petBoxTypeId);
                        String petBoxTypeName = airBox.getBoxTypeName();
                        pet.put("petBoxTypeName",petBoxTypeName);
                        petBoxPriceAll+=Integer.valueOf(singleBoxPrice);
                    }
                }

            }
            if(selDWJYHG!=null && (selDWJYHG.equals("true")||selDWJYHG.equals("on"))){
                quarantineCertPrice +=  Integer.valueOf(quarantineCertCost);
            }

        }
        int rateA = Integer.valueOf(rateIntervalA);
        int rateB = Integer.valueOf(rateIntervalB);
        int rateC = Integer.valueOf(rateIntervalC);
        int weightPrice = 0;
        if(totalWeight<=40){
            if(totalWeight<=20){
                if(totalWeight>8){
                    weightPrice = (totalWeight-8) * rateA;
                }
            }else{
                weightPrice =(totalWeight-20) *rateB + (20-8) * rateA;
            }
        }else{
            weightPrice = (totalWeight-40)*rateC + (40-20) *rateB + (20-8) * rateA;
        }
        int overWeightPrice = overWeightCount* Integer.valueOf(singleOverWeightRate);


        ticketPrice = String.valueOf(weightPrice+overWeightPrice+basePrice+quarantineCertPrice);
        //int weightPrice = (totalWeight-40>0?totalWeight-40:0)*rateC +
         //       *rateB +
          //      (totalWeight-40>0?totalWeight-40:0)*rateA;
        String petBoxPrice = String.valueOf(petBoxPriceAll);

        String placePrice = "0";
        int placePriceInt = 0;
        if(selSmjc!=null && (selSmjc.equals("true")||selSmjc.equals("on"))){
            /*Map placeMap = new HashMap();
            placeMap.put("detailCode",placeAreaCode);
            StartPlaceDetail dt = startPlaceDetailService.selectDetailByDetailCode(placeMap);
            if(dt != null){
                placePrice = dt.getPrice();
            }*/
            int distance = Integer.valueOf(placeDistance);
            //  0-10km	免费	免费
            if(distance>0 && distance<=10){
                // 10-20km	40元/件	20元/件
                placePriceInt= 0;
            }else if(distance>10 && distance<=20){
                // 10-20km	40元/件	20元/件
                placePriceInt= 40 + 20 * (petNum - 1);
            }else if(distance>20 && distance<=25){
                // 20-25km	50元/件	20元/件
                placePriceInt= 50 + 20 * (petNum - 1);
            }else if(distance>25 && distance<=30){
                // 25-30km	60元/件	20元/件
                placePriceInt= 60 + 20 * (petNum - 1);
            }else if(distance>30 && distance<=35){
                // 30-35km	70元/件	20元/件
                placePriceInt= 70 + 20 * (petNum - 1);
            }else if(distance>35 && distance<=40){
                // 35-40km	80元/件	20元/件
                placePriceInt= 80 + 20 * (petNum - 1);
            }else if(distance>40 && distance<=45){
                // 40-45km	90元/件	20元/件
                placePriceInt= 90 + 20 * (petNum - 1);
            }else if(distance>45 && distance<=50){
                // 45-50km	100元/件	20元/件
                placePriceInt= 100 + 20 * (petNum - 1);
            }else if(distance>50){
                placePriceInt=1000000000;
            }
            placePrice = String .valueOf(placePriceInt);
        }

        Double totalPrice = Double.parseDouble(ticketPrice) +
                Double.parseDouble(petBoxPrice) +
                Double.parseDouble(placePrice) +
                Double.parseDouble(insuredPrice) ;
        Map map = new HashMap();
        map.put("ticketPrice",ticketPrice);
        map.put("petBoxPrice",petBoxPrice);
        map.put("petLst",petLst);
        //map.put("boxTypeId",boxTypeId);
        //map.put("boxTypeName",boxTypeName);
        map.put("placePrice",placePrice);
        map.put("insuredPrice",insuredPrice);
        map.put("totalPrice",totalPrice.toString());
        return map;
    }
}
