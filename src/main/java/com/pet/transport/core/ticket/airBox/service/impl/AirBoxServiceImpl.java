package com.pet.transport.core.ticket.airBox.service.impl;

import com.pet.transport.core.ticket.airBox.dao.IAirBoxDao;
import com.pet.transport.core.ticket.airBox.service.IAirBoxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("airBoxServiceImpl")
public class AirBoxServiceImpl implements IAirBoxService {
    @Autowired
    private IAirBoxDao airBoxDao;

    public List selectAirBoxListByFeature(Map map) {
        return airBoxDao.selectAirBoxListByFeature(map);
    }
}
