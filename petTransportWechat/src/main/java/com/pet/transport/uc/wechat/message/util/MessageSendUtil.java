package com.pet.transport.uc.wechat.message.util;

import com.pet.transport.common.contants.URLContants;
import com.pet.transport.uc.wechat.core.pay.WXPayUtil;
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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zimok
 * @Title: MessageSendUtil
 * @ProjectName petTransport
 * @Description: TODO
 * @date 2019/2/171:43
 */
public class MessageSendUtil {

    private Log logger = LogFactory.getLog(MessageSendUtil.class);

    private static MessageSendUtil messageUtil = null;

    public static synchronized MessageSendUtil getInstance() {
        if (messageUtil == null) {
            messageUtil = new MessageSendUtil();
        }
        return messageUtil;
    }

    // 非Controller 注入方式
    @PostConstruct
    public void init() {
        messageUtil = this;
        messageUtil.templateMessageServiceImpl = this.templateMessageServiceImpl;
    }

    WeChatUtil weChatUtil = WeChatUtil.getInstance();
    @Autowired
    @Qualifier("templateMessageServiceImpl")
    private ITemplateMessageService templateMessageServiceImpl;



    public void sendKfMessage(String json){
        //发送消息的url
        String access_token = weChatUtil.getAccessToken();
        String url="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+access_token;
        logger.debug(" url: "+url + " json: "+json);

        String httpsRequest = WXPayUtil.httpsRequest(url, "POST", json);
    }


    public void sendShouhuoAdminMessage(Map param){
        param.put("first","欢迎进入收货管理页面，详情请点击");
        param.put("keyword1",  "点击进入收货管理");
        param.put("keyword2",  "点击进入收货管理");
        param.put("remark",  "请及时安排人员进行收货");
        param.put("url",  URLContants.WEB_URL+"/ticket/orderAdmin/adminOrderSumbit");

        sendAdminMessage(param);
    }
    public void sendPrintAdminMessage(Map param){
        param.put("first","欢迎进入打印管理页面，详情请点击");
        param.put("keyword1",  "点击进入打印管理");
        param.put("keyword2",  "点击进入打印管理");
        param.put("remark",  "点击打印后选择具体的要打印的订单，点击打印，点击右上角的共享，给电脑，在电脑端进行打印，特别注意，可以在最后输入数字1-9改变打印位置");
        param.put("url",  URLContants.WEB_URL+"/ticket/orderAdmin/adminOrderPayed");
        sendAdminMessage(param);
    }

    private void sendAdminMessage(Map param){
        List<String> lst = new ArrayList<String>();
        String openid = (String) param.get("openid");
        if( logger.isDebugEnabled()){
            logger.debug("-----sendShouhuoAdminMessage---要推送的接受openid:"+openid);
        }
        //String openid="oYey51VVExpQZ38giMltpW_TIo2M";
        // 获取基础支持的access_token
        String access_token = weChatUtil.getAccessToken();
        // 发送模板消息
        String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(TemplateContants.TEM_ORDER_ADMIN);
        wechatTemplate.setTouser(openid);
        wechatTemplate.setUrl((String)param.get("url"));
        Map<String,WechatTemplateData> mapdata = new HashMap<String,WechatTemplateData>();
        // 封装模板数据
        WechatTemplateData first = new WechatTemplateData();
        first.setValue((String)param.get("first"));
        first.setColor("#173177");
        mapdata.put("first", first);

        WechatTemplateData keyword1 = new WechatTemplateData();
        keyword1.setValue((String)param.get("keyword1"));
        keyword1.setColor("#173177");
        mapdata.put("keyword1", keyword1);

        WechatTemplateData keyword2 = new WechatTemplateData();
        keyword2.setValue((String)param.get("keyword2"));
        keyword2.setColor("#173177");
        mapdata.put("keyword2", keyword2);


        WechatTemplateData remark = new WechatTemplateData();
        remark.setValue((String)param.get("remark"));
        remark.setColor("#173177");
        mapdata.put("remark", remark);

        wechatTemplate.setData(mapdata);
        if( logger.isDebugEnabled()){
            logger.debug("-----sendShouhuoAdminMessage----开始推送消息access_token："+access_token);
        }
        WechatResponse response = messageUtil.templateMessageServiceImpl.sendTemplateMessage(access_token,wechatTemplate);
        if( logger.isDebugEnabled()){
            logger.debug("-----sendShouhuoAdminMessage---结束推送消息---------");
        }

    }


}
