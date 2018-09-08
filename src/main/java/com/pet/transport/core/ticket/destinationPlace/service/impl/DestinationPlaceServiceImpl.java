package com.pet.transport.core.ticket.destinationPlace.service.impl;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.ticket.destinationPlace.dao.IDestinationPlaceDao;
import com.pet.transport.core.ticket.destinationPlace.po.DestinationPlace;
import com.pet.transport.core.ticket.destinationPlace.service.IDestinationPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("destinationPlaceServiceImpl")
public class DestinationPlaceServiceImpl implements IDestinationPlaceService {
    @Autowired
    private IDestinationPlaceDao destinationPlaceDao;

    public void updateDestinationPlace(Map paramMap) {
        destinationPlaceDao.updateDestinationPlace(paramMap);
    }

    public List<DestinationPlace> selectDestinationPlaceByStartPlace(Map paramMap) {
        List<DestinationPlace> spLst =destinationPlaceDao.selectDestinationPlaceByStartPlace(paramMap);
        return spLst;
    }
    public String selectDestinationPlaceByStartPlaceJson(Map paramMap) {
        List<DestinationPlace> spLst =selectDestinationPlaceByStartPlace(paramMap);
        Map simpleMap = new HashMap();
        if(spLst != null && spLst.size()>0){
            for(DestinationPlace sp:spLst){
               String simple = sp.getDestinationPlaceSimple();
               if (simpleMap.containsKey(simple)){
                   List newSpLst = (List) simpleMap.get(simple);
                   newSpLst.add(sp);
                   simpleMap.put(simple,newSpLst);
               }else{
                   List newSpLst = new ArrayList();
                   newSpLst.add(sp);
                   simpleMap.put(simple,newSpLst);
               }
            }
        }
        String ret = "";
        if(simpleMap!=null){
            ret = DataConvertUtil.convertMapToJson(simpleMap);
        }
        return ret;
    }
    public DestinationPlace selectDestinationPlaceByStartPlaceAndDistPlace(Map paramMap) {
       return destinationPlaceDao.selectDestinationPlaceByStartPlaceAndDistPlace(paramMap);
    }
    public void addDestinationPlace(Map paramMap) {
        destinationPlaceDao.addDestinationPlace(paramMap);
    }
}
