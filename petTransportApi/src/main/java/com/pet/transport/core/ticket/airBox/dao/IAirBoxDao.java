package com.pet.transport.core.ticket.airBox.dao;

import java.util.List;
import java.util.Map;

public interface IAirBoxDao {
     List selectAirBoxListByFeature(Map map);
    List selectAllAirBoxList(Map map);

}
