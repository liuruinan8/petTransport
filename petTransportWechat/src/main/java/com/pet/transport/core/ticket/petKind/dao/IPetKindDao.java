package com.pet.transport.core.ticket.petKind.dao;

import com.pet.transport.core.ticket.petKind.po.PetKind;

import java.util.List;
import java.util.Map;

public interface IPetKindDao {
    public List<PetKind> selectAllPetKind(Map paramMap);
}
