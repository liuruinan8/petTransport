package com.pet.transport.core.ticket.startPlace.dao;

import com.pet.transport.core.ticket.startPlace.po.StartPlace;

import java.util.List;
import java.util.Map;

public interface IStartPlaceDao {
    public void updateStartPlace(Map startPlace);

    public List<StartPlace> selectAllStartPlace(Map paramMap);

    public void addStartPlace(Map paramMap);
}
