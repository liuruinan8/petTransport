package com.pet.transport.uc.wechat.message.po;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author zimok
 * @Title: ImageMessage
 * @ProjectName petTransport
 * @Description: TODO
 * @date 2019/2/1720:37
 */
@XStreamAlias("ImageMessage ")
public class ImageMessage extends BaseMessage{
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ImageMessage [image=" + image + "]";
    }
}
