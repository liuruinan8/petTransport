package com.pet.transport.core.ticket.startPlaceDetail.service.impl;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.ticket.startPlaceDetail.dao.IStartPlaceDetailDao;
import com.pet.transport.core.ticket.startPlaceDetail.po.StartPlaceDetail;
import com.pet.transport.core.ticket.startPlaceDetail.service.IStartPlaceDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("startPlaceDetailServiceImpl")
public class StartPlaceDetailServiceImpl implements IStartPlaceDetailService {
    @Autowired
    private IStartPlaceDetailDao startPlaceDetailDao;

    public List<StartPlaceDetail> selectAllDetailStartPlace(Map paramMap){
       return startPlaceDetailDao.selectAllDetailStartPlace(paramMap);
    }
    public String selectAllDetailStartPlaceToJson(Map paramMap) {
        List<StartPlaceDetail> spLst =selectAllDetailStartPlace(paramMap);

        List newSpLst = new ArrayList();
        if(spLst != null && spLst.size()>0){
            for(StartPlaceDetail sp:spLst){
                String detailCode = sp.getDetailCode();
                String detailName = sp.getDetailName();
                Map simpleMap = new HashMap();
                simpleMap.put("code",detailCode);
                simpleMap.put("name",detailName);
                newSpLst.add(simpleMap);
            }
        }
        String ret = "";
        if(newSpLst!=null){
            ret = DataConvertUtil.convertBeanToJson(newSpLst);
        }
        return ret;
    }

    public StartPlaceDetail selectDetailByDetailCode(Map paramMap){
        return startPlaceDetailDao.selectDetailByDetailCode(paramMap);
    }
}
