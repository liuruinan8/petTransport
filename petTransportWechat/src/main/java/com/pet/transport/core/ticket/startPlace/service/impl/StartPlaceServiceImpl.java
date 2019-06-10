package com.pet.transport.core.ticket.startPlace.service.impl;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.ticket.startPlace.dao.IStartPlaceDao;
import com.pet.transport.core.ticket.startPlace.po.StartPlace;
import com.pet.transport.core.ticket.startPlace.service.IStartPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("startPlaceServiceImpl")
public class StartPlaceServiceImpl implements IStartPlaceService {
    @Autowired
    private IStartPlaceDao startPlaceDao;

    public void updateStartPlace(Map paramMap) {
        startPlaceDao.updateStartPlace(paramMap);
    }

    public List<StartPlace> selectAllStartPlace(Map paramMap) {
        List<StartPlace> spLst =startPlaceDao.selectAllStartPlace(paramMap);
        return spLst;
    }
    public String selectAllStartPlaceToJson(Map paramMap) {
        List<StartPlace> spLst =selectAllStartPlace(paramMap);
        Map simpleMap = new HashMap();
        if(spLst != null && spLst.size()>0){
            for(StartPlace sp:spLst){
               String simple = sp.getStartPlaceSimple();
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
    public void addStartPlace(Map paramMap) {
        startPlaceDao.addStartPlace(paramMap);
    }
}
