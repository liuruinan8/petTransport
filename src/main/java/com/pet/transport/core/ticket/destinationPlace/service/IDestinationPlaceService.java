package com.pet.transport.core.ticket.destinationPlace.service;

import com.pet.transport.core.ticket.destinationPlace.po.DestinationPlace;

import java.util.List;
import java.util.Map;

public interface IDestinationPlaceService {
    public void updateDestinationPlace(Map DestinationPlace);

    public List<DestinationPlace> selectDestinationPlaceByStartPlace(Map paramMap);

    public void addDestinationPlace(Map paramMap);

    public String selectDestinationPlaceByStartPlaceJson(Map paramMap);
}