package com.pet.transport.core.ticket.startPlaceDetail.service;

import com.pet.transport.core.ticket.startPlaceDetail.po.StartPlaceDetail;

import java.util.List;
import java.util.Map;

public interface IStartPlaceDetailService {
    List<StartPlaceDetail> selectAllDetailStartPlace(Map paramMap);

    public String selectAllDetailStartPlaceToJson(Map paramMap);

    public StartPlaceDetail selectDetailByDetailCode(Map paramMap);
}
