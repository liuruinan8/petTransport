package com.pet.transport.uc.wechat.template.service;

import com.pet.transport.uc.wechat.template.po.WechatResponse;
import com.pet.transport.uc.wechat.template.po.WechatTemplate;

public interface ITemplateMessageService {

    public WechatResponse sendTemplateMessage(String accessToken, WechatTemplate weixinTemplate);
}
