package com.pet.transport.uc.wechat.message.po;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 回复消息之图文消息
 * @author 32950745
 *
 */
@XStreamAlias("Article")
public class Article extends BaseMessage{
    @XStreamAlias("Title")
    private String title = "";

    @XStreamAlias("Description")
    private String description = "";

    @XStreamAlias("PicUrl")
    private String picUrl = "";

    @XStreamAlias("Url")
    private String url = "";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}