package com.pet.transport.core.ticket.petKind.service.impl;

import com.pet.transport.common.util.DataConvertUtil;
import com.pet.transport.core.ticket.petKind.dao.IPetKindDao;
import com.pet.transport.core.ticket.petKind.po.PetKind;
import com.pet.transport.core.ticket.petKind.service.IPetKindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("petKindServiceImpl")
public class PetKindServiceImpl implements IPetKindService {
    @Autowired
    private IPetKindDao petKindDao;

    public List<PetKind> selectAllPetKind(Map paramMap) {
        List<PetKind> spLst =petKindDao.selectAllPetKind(paramMap);
        return spLst;
    }
    public String selectAllPetKindToJson(Map paramMap) {
        List<PetKind> spLst =selectAllPetKind(paramMap);
        Map simpleMap = new HashMap();
        if(spLst != null && spLst.size()>0){
            for(PetKind sp:spLst){
                String simple = sp.getKindNameSimple();
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
}
