package com.pet.transport.core.ticket.airBox.service;

import java.util.List;
import java.util.Map;

public interface IAirBoxService {
    List selectAirBoxListByFeature(Map map);

    List selectAllAirBoxList(Map map);

    String selectAllAirBoxListJsonData(Map paramMap);
}
