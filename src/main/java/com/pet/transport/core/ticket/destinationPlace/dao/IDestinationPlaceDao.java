package com.pet.transport.core.ticket.destinationPlace.dao;

import com.pet.transport.core.ticket.destinationPlace.po.DestinationPlace;

import java.util.List;
import java.util.Map;

public interface IDestinationPlaceDao {
    
    public void updateDestinationPlace(Map DestinationPlace);

    public List<DestinationPlace> selectDestinationPlaceByStartPlace(Map paramMap);

    public void addDestinationPlace(Map paramMap);
}
