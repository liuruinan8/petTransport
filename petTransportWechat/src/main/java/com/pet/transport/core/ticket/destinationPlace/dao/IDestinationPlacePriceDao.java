package com.pet.transport.core.ticket.destinationPlace.dao;

import com.pet.transport.core.ticket.destinationPlace.po.DestinationPlacePrice;

import java.util.Map;

/**
 * @author zimok
 * @Title: IDestinationPlacePriceDao
 * @ProjectName petTransport
 * @Description: 目的地相关票价接口
 * @date 2019/1/1120:41
 */
public interface IDestinationPlacePriceDao {
    public DestinationPlacePrice selectPriceByStartPlaceAndDistPlaceAndUseType(Map paramMap);
}
