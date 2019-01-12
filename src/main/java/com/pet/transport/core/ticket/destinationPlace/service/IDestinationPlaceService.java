package com.pet.transport.core.ticket.destinationPlace.service;

import com.pet.transport.core.ticket.destinationPlace.po.DestinationPlace;
import com.pet.transport.core.ticket.destinationPlace.po.DestinationPlacePrice;

import java.util.List;
import java.util.Map;

public interface IDestinationPlaceService {
    public void updateDestinationPlace(Map DestinationPlace);

    public List<DestinationPlace> selectDestinationPlaceByStartPlace(Map paramMap);

    public void addDestinationPlace(Map paramMap);

    public String selectDestinationPlaceByStartPlaceJson(Map paramMap);

    public DestinationPlace selectDestinationPlaceByStartPlaceAndDistPlace(Map param);

    public DestinationPlacePrice selectPriceByStartPlaceAndDistPlaceAndUseType(Map param);
}
