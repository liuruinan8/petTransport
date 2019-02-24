package com.pet.transport.uc.wechat.message.po;

/**
 * @author zimok
 * @Title: Image
 * @ProjectName petTransport
 * @Description: TODO
 * @date 2019/2/1720:39
 */
public class Image {
    private String media_id;

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    @Override
    public String toString() {
        return "Image [media_id=" + media_id + "]";
    }
}
