package com.pet.transport.core.ticket.startPlace.service;

import com.pet.transport.core.ticket.startPlace.po.StartPlace;

import java.util.List;
import java.util.Map;

public interface IStartPlaceService {
    public void updateStartPlace(Map startPlace);

    public List<StartPlace> selectAllStartPlace(Map paramMap);

    public void addStartPlace(Map paramMap);

    public String selectAllStartPlaceToJson(Map paramMap);
}
