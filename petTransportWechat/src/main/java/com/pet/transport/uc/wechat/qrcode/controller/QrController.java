package com.pet.transport.uc.wechat.qrcode.controller;

import com.pet.transport.uc.user.util.UserUtil;
import com.pet.transport.uc.wechat.core.util.WeChatUtil;
import com.pet.transport.uc.wechat.qrcode.service.IQRCodeService;
import com.pet.transport.uc.wechat.template.contants.TemplateContants;
import com.pet.transport.uc.wechat.template.po.WechatResponse;
import com.pet.transport.uc.wechat.template.po.WechatTemplate;
import com.pet.transport.uc.wechat.template.po.WechatTemplateData;
import com.pet.transport.uc.wechat.template.service.ITemplateMessageService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/qr")
public class QrController {
    private static Logger log = LoggerFactory.getLogger(QrController.class);

    @Autowired
    @Qualifier("QRCodeServiceImpl")
    private IQRCodeService qrCodeService;

    WeChatUtil weChatUtil = WeChatUtil.getInstance();
    @Autowired
    @Qualifier("templateMessageServiceImpl")
    private ITemplateMessageService templateMessageServiceImpl;

    @RequestMapping(value = "/tempQr", method = RequestMethod.POST)
    @ResponseBody
    public Object createTempQrCode(String accessToken, String userId,int sceneId) {

        //根据userId 创建一张表存取两者之间的关联关系 供后期使用

        JSONObject result = qrCodeService.createTempQrCode(accessToken,userId,sceneId) ;
        //此url位生成临时二维码url,扫码后可进行公众账号关注
        String resultUrl = result.getString("url");
        if(!StringUtils.isEmpty(resultUrl)){
            result.put("attentionUrl", resultUrl);
            result.put("expireSeconds", result.getString("expire_seconds"));
        }else{
            result.put("status", "error");
            result.put("errcode", result.getString("errcode"));
            result.put("errmsg", result.getString("errmsg"));
        }

        return result;
    }
    @RequestMapping(value = "/creatTempQr")
    public Object createTempQrCode() {
        String accessToken=weChatUtil.getAccessToken();
        Map userMap =UserUtil.getInstance().getLoginUserMap();
        String userId = "";
        if(userMap!=null && !userMap.isEmpty()){
            if(userMap.containsKey("userId")){
                userId = (String) userMap.get("userId");
            }
        }
        //根据userId 创建一张表存取两者之间的关联关系 供后期使用
        Integer sceneId=8888;
        JSONObject resultToMv = new JSONObject();
        JSONObject result = qrCodeService.createTempQrCode(accessToken,userId,sceneId) ;
        //此url位生成临时二维码url,扫码后可进行公众账号关注
        if(result.containsKey("url")){
            String resultUrl = result.getString("url");
            if(!StringUtils.isEmpty(resultUrl)){
                resultToMv.put("status", "");
                resultToMv.put("msg", "二维码生成成功，");
                sendQrCreateTemplate(userId,result);
                String uri="redirect:https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+result.getString("ticket");
                return uri;
            }
        }else{
            resultToMv.put("status", "error");
            resultToMv.put("errcode", result.getString("errcode"));
            resultToMv.put("errmsg", result.getString("errmsg"));
        }

        return resultToMv;
    }
    private void sendQrCreateTemplate(String userId,JSONObject result){
        // 获取基础支持的access_token
        String access_token = weChatUtil.getAccessToken();
        // 发送模板消息
        String resultUrl2 = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="+access_token;
        // 封装基础数据
        WechatTemplate wechatTemplate = new WechatTemplate();
        wechatTemplate.setTemplate_id(TemplateContants.TEM_QRCODE_CREATE);
        wechatTemplate.setTouser(userId);
        //https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGk7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyM3d3Z1FMeEllVmwxVGZyQ2hyMWIAAgTHVOZbAwQIBwAA
        wechatTemplate.setUrl("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+result.getString("ticket"));
        Map<String,WechatTemplateData> mapdata = new HashMap<String,WechatTemplateData>();
        // 封装模板数据
        WechatTemplateData first = new WechatTemplateData();
        first.setValue("您好，这个是属于你自己的二维码。");
        first.setColor("#173177");
        mapdata.put("first", first);

        WechatTemplateData keyword1 = new WechatTemplateData();
        keyword1.setValue(result.getString("url"));
        keyword1.setColor("#173177");
        mapdata.put("keyword1", keyword1);

        WechatTemplateData keyword2 = new WechatTemplateData();
        //int es=Integer.valueOf(result.getString("expire_seconds"));
        //int esday= es/86400;
        keyword2.setValue("有效期为：30天");
        keyword2.setColor("#173177");
        mapdata.put("keyword2", keyword2);

        WechatTemplateData remark = new WechatTemplateData();
        remark.setValue("欢迎邀请您的好友，共同享受宠物运送，同时您将获得一定的红包奖励哦，抓紧开始邀请吧");
        remark.setColor("#173177");
        mapdata.put("remark", remark);

        wechatTemplate.setData(mapdata);
        WechatResponse response = templateMessageServiceImpl.sendTemplateMessage(access_token,wechatTemplate);
        if (0 != response.getErrcode()) {
            //System.out.println(response.getErrmsg());
        }else{
            //System.out.println("Susscess");
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