package com.pet.transport.uc.wechat.qrcode.service;

import net.sf.json.JSONObject;

public interface IQRCodeService {

    public JSONObject createTempQrCode(String accessToken, String userId, Integer sceneId);
}
