package com.pet.transport.core.order.util;

import com.pet.transport.common.contants.URLContants;
import com.pet.transport.uc.wechat.core.util.WeChatUtil;
import com.pet.transport.uc.wechat.template.contants.TemplateContants;
import com.pet.transport.uc.wechat.template.po.WechatResponse;
import com.pet.transport.uc.wechat.template.po.WechatTemplate;
import com.pet.transport.uc.wechat.template.po.WechatTemplateData;
import com.pet.transport.uc.wechat.template.service.ITemplateMessageService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderMessageUtil {
    private Log logger = LogFactory.getLog(OrderMessageUtil.class);

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
        if( logger.isDebugEnabled()){
            logger.debug("--------要推送的接受openid:"+openid);
        }
        //String openid="oYey51VVExpQZ38giMltpW_TIo2M";
        // 获取基础支持的access_token
        String access_token = weChatUtil.getAccessToken();
        // 发送模板消息
        String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(TemplateContants.TEM_ORDER_SUBMIT_SUCCESS);
        wechatTemplate.setTouser(openid);
        wechatTemplate.setUrl(URLContants.WEB_URL+"/ticket/order/mine");
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
        remark.setValue("如有问题请致电客服或直接在微信留言，我们将第一时间为您服务！");
        remark.setColor("#173177");
        mapdata.put("remark", remark);

        wechatTemplate.setData(mapdata);
        if( logger.isDebugEnabled()){
            logger.debug("---------开始推送消息access_token："+access_token);
        }
        WechatResponse response = orderMessageUtil.templateMessageServiceImpl.sendTemplateMessage(access_token,wechatTemplate);
        if( logger.isDebugEnabled()){
            logger.debug("--------结束推送消息---------");
        }
        return response;
    }
    public void sendOrderSaveSuccessToAdminMessage(Map order){
        List<String> lst = new ArrayList<String>();
        lst.add("oYey51TgN4A3f8-jxMNH8ygIhM3k");
        lst.add("oYey51aFK-ypH7rGKXS9QBywxMkE");
        lst.add("oYey51XVMlPokz6OQzQKmoSNYGJY");

        for(String openid : lst){
            String orderId = (String) order.get("orderId");
            if( logger.isDebugEnabled()){
                logger.debug("--------要推送的接受openid:"+openid);
            }
            //String openid="oYey51VVExpQZ38giMltpW_TIo2M";
            // 获取基础支持的access_token
            String access_token = weChatUtil.getAccessToken();
            // 发送模板消息
            String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
            // 封装基础数据
            WechatTemplate wechatTemplate = new WechatTemplate();
            wechatTemplate.setTemplate_id(TemplateContants.TEM_ORDER_DAISHOUHUO);
            wechatTemplate.setTouser(openid);
            wechatTemplate.setUrl(URLContants.WEB_URL+"/ticket/orderaAdmin/adminOrderDetailArrival?orderId="+orderId);
            Map<String,WechatTemplateData> mapdata = new HashMap<String,WechatTemplateData>();
            // 封装模板数据
            WechatTemplateData first = new WechatTemplateData();
            first.setValue("您好，您有新的客户订单需要收货，详情请点击");
            first.setColor("#173177");
            mapdata.put("first", first);

            WechatTemplateData keyword1 = new WechatTemplateData();
            String deliverName = (String)order.get("deliverName");
            keyword1.setValue(deliverName);
            keyword1.setColor("#173177");
            mapdata.put("keyword1", keyword1);

            WechatTemplateData keyword2 = new WechatTemplateData();
            String orderNo = (String)order.get("orderNo");
            keyword2.setValue(orderNo);
            keyword2.setColor("#173177");
            mapdata.put("keyword2", keyword2);

            WechatTemplateData keyword3 = new WechatTemplateData();
            keyword3.setValue("待确定");
            keyword3.setColor("#173177");
            mapdata.put("keyword3", keyword3);

            WechatTemplateData keyword4 = new WechatTemplateData();
            keyword4.setValue("未付款");
            keyword4.setColor("#173177");
            mapdata.put("keyword4", keyword4);

            WechatTemplateData remark = new WechatTemplateData();
            String deliverMobile = (String)order.get("deliverMobile");
            remark.setValue("请及时安排人员进行收货，客户联系电话为："+deliverMobile);
            remark.setColor("#173177");
            mapdata.put("remark", remark);

            wechatTemplate.setData(mapdata);
            if( logger.isDebugEnabled()){
                logger.debug("---------开始推送消息access_token："+access_token);
            }
            WechatResponse response = orderMessageUtil.templateMessageServiceImpl.sendTemplateMessage(access_token,wechatTemplate);
            if( logger.isDebugEnabled()){
                logger.debug("--------结束推送消息---------");
            }
        }

    }
}
