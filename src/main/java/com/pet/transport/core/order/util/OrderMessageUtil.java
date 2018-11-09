package com.pet.transport.core.order.util;

import com.pet.transport.uc.wechat.core.util.WeChatUtil;
import com.pet.transport.uc.wechat.template.contants.TemplateContants;
import com.pet.transport.uc.wechat.template.po.WechatResponse;
import com.pet.transport.uc.wechat.template.po.WechatTemplate;
import com.pet.transport.uc.wechat.template.po.WechatTemplateData;
import com.pet.transport.uc.wechat.template.service.ITemplateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class OrderMessageUtil {

    private static OrderMessageUtil orderMessageUtil = null;

    public static synchronized OrderMessageUtil getInstance() {
        if (orderMessageUtil == null) {
            orderMessageUtil = new OrderMessageUtil();
        }
        return orderMessageUtil;
    }

    // 非Controller 注入方式
    @PostConstruct
    public void init() {
        orderMessageUtil = this;
        orderMessageUtil.templateMessageServiceImpl = this.templateMessageServiceImpl;
    }

    WeChatUtil weChatUtil = WeChatUtil.getInstance();
    @Autowired
    @Qualifier("templateMessageServiceImpl")
    private ITemplateMessageService templateMessageServiceImpl;

    public WechatResponse sendOrderSaveSuccessMessage(Map order){
        String openid = (String) order.get("userId");
        //String openid="oYey51VVExpQZ38giMltpW_TIo2M";
        // 获取基础支持的access_token
        String access_token = weChatUtil.getAccessToken();
        // 发送模板消息
        String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(TemplateContants.TEM_ORDER_SUBMIT_SUCCESS);
        wechatTemplate.setTouser(openid);
        wechatTemplate.setUrl("http://tw.xxx.com/member/member.html?id=");
        Map<String,WechatTemplateData> mapdata = new HashMap<String,WechatTemplateData>();
        // 封装模板数据
        WechatTemplateData first = new WechatTemplateData();
        first.setValue("您的订单已保存成功，待支付金额为支付"+order.get("totalPrice")+"元。");
        first.setColor("#173177");
        mapdata.put("first", first);

        WechatTemplateData orderID = new WechatTemplateData();
        orderID.setValue(order.get("orderNo")+"");
        orderID.setColor("#173177");
        mapdata.put("orderID", orderID);

        WechatTemplateData orderMoneySum = new WechatTemplateData();
        orderMoneySum.setValue(order.get("totalPrice")+"元。");
        orderMoneySum.setColor("#173177");
        mapdata.put("orderMoneySum", orderMoneySum);

        WechatTemplateData remark = new WechatTemplateData();
        remark.setValue("如有问题请致电18668929353或直接在微信留言，我们将第一时间为您服务！");
        remark.setColor("#173177");
        mapdata.put("remark", remark);

        wechatTemplate.setData(mapdata);
        WechatResponse response = orderMessageUtil.templateMessageServiceImpl.sendTemplateMessage(access_token,wechatTemplate);
        return response;
    }
}
