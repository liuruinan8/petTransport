package com.pet.transport.uc.wechat.template.po;

import java.util.Map;

public class WechatTemplate {

    private String touser;

    private String template_id;

    private String url;

    private Map<String, WechatTemplateData> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, WechatTemplateData> getData() {
        return data;
    }

    public void setData(Map<String, WechatTemplateData> data) {
        this.data = data;
    }
}