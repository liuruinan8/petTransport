package com.pet.transport.uc.wechat.template.test;

import com.pet.transport.common.test.SpringTestCase;
import com.pet.transport.uc.wechat.core.util.WeChatUtil;
import com.pet.transport.uc.wechat.template.contants.TemplateContants;
import com.pet.transport.uc.wechat.template.po.WechatResponse;
import com.pet.transport.uc.wechat.template.po.WechatTemplate;
import com.pet.transport.uc.wechat.template.po.WechatTemplateData;
import com.pet.transport.uc.wechat.template.service.ITemplateMessageService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

public class TemplateSendTest extends SpringTestCase {
    WeChatUtil weChatUtil = WeChatUtil.getInstance();
    @Autowired
    @Qualifier("templateMessageServiceImpl")
    private ITemplateMessageService templateMessageServiceImpl;
    /**
     * 用户注册成功的模板消息
     */
    @Test
    public void registTemplate(){
        String openid="oYey51VVExpQZ38giMltpW_TIo2M";
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
        first.setValue("您好，您已注册成为XXX平台用户。");
        first.setColor("#173177");
        mapdata.put("first", first);

        WechatTemplateData OrderSn = new WechatTemplateData();
        OrderSn.setValue("123456789");
        OrderSn.setColor("#173177");
        mapdata.put("OrderSn", OrderSn);

        WechatTemplateData OrderStatus = new WechatTemplateData();
        OrderStatus.setValue("已下单");
        OrderStatus.setColor("#173177");
        mapdata.put("OrderStatus", OrderStatus);

        WechatTemplateData remark = new WechatTemplateData();
        remark.setValue("欢迎您的加入，请及时购买会员并完善资料>>");
        remark.setColor("#173177");
        mapdata.put("remark", remark);

        wechatTemplate.setData(mapdata);
        WechatResponse response = templateMessageServiceImpl.sendTemplateMessage(access_token,wechatTemplate);
        if (0 != response.getErrcode()) {
            System.out.println(response.getErrmsg());
        }else{
            System.out.println("Susscess");
        }

        /*String toString = JacksonUtil.toJson(wechatTemplate).toString();
        String json2 = HttpUtil.post(resultUrl2,toString);
        JSONObject jsonObject2 = JacksonUtil.toEntity(json2, JSONObject.class);
        int result = 0;
        if (null != jsonObject2) {
            if (0 != jsonObject2.getInt("errcode")) {
                result = jsonObject2.getInt("errcode");
                StaticLog.error("错误 errcode:{} errmsg:{}", jsonObject2.getInt("errcode"), jsonObject2.get("errmsg").toString());
            }
        }
        StaticLog.info("模板消息发送结果："+result);*/
    }


}
