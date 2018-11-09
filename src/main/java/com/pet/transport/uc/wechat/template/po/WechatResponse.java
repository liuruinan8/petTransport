package com.pet.transport.uc.wechat.template.po;

public class WechatResponse {
    private String msgid;
    private int errcode;
    private String errmsg;

    public String getMsgid() {
        return msgid;
    }
    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }
    public int getErrcode() {
        return errcode;
    }
    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }
    public String getErrmsg() {
        return errmsg;
    }
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer("WeixinResponse[msgid=");
        buf.append(msgid)
                .append(",errcode=").append(errcode)
                .append(",errmsg=").append(errmsg)
                .append("]");
        return buf.toString();
    }

}