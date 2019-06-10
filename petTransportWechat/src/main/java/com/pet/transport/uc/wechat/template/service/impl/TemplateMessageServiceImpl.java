package com.pet.transport.uc.wechat.template.service.impl;

import com.pet.transport.uc.wechat.template.po.WechatResponse;
import com.pet.transport.uc.wechat.template.po.WechatTemplate;
import com.pet.transport.uc.wechat.template.service.ITemplateMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service("templateMessageServiceImpl")
public class TemplateMessageServiceImpl implements ITemplateMessageService {
    private static Logger log = LoggerFactory.getLogger(TemplateMessageServiceImpl.class);
    private RestTemplate restTemplate ;

    private String serviceHost = "https://api.weixin.qq.com";

    public TemplateMessageServiceImpl() {
        restTemplate = new RestTemplate();
    }

    public WechatResponse sendTemplateMessage(String accessToken, WechatTemplate weixinTemplate) {
        WechatResponse weixinResponse = null;
        String url = new StringBuffer(serviceHost).append("/cgi-bin/message/template/send?access_token=")
                .append(accessToken).toString();
        weixinResponse = restTemplate.postForObject(url, weixinTemplate, WechatResponse.class,new HashMap<String,String>());
        return weixinResponse;
    }


}
