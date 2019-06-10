package com.pet.transport.uc.wechat.qrcode.dao;

import com.pet.transport.uc.wechat.qrcode.po.QRCode;

import java.util.List;
import java.util.Map;

public interface QRCodeDao {


    public int addQRCode(Map map);

    public List<QRCode> selectQRCodeByUserId(Map map);
}
