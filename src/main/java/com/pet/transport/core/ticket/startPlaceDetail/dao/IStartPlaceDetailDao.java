package com.pet.transport.core.ticket.startPlaceDetail.dao;

import com.pet.transport.core.ticket.startPlaceDetail.po.StartPlaceDetail;

import java.util.List;
import java.util.Map;

public interface IStartPlaceDetailDao {

    public List<StartPlaceDetail> selectAllDetailStartPlace(Map paramMap);

    public StartPlaceDetail selectDetailByDetailCode(Map paramMap);
}
