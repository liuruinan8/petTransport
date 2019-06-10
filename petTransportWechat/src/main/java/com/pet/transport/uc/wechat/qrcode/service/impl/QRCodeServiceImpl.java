package com.pet.transport.uc.wechat.qrcode.service.impl;

import com.pet.transport.common.util.SnowflakeIdWorker;
import com.pet.transport.uc.wechat.qrcode.dao.QRCodeDao;
import com.pet.transport.uc.wechat.qrcode.po.QRCode;
import com.pet.transport.uc.wechat.qrcode.service.IQRCodeService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("QRCodeServiceImpl")
public class QRCodeServiceImpl implements IQRCodeService {
    private RestTemplate restTemplate ;
    private Log logger = LogFactory.getLog(QRCodeServiceImpl.class);

    private String serviceHost = "https://api.weixin.qq.com";

    public QRCodeServiceImpl() {
        restTemplate = new RestTemplate();
    }


    @Autowired
    private QRCodeDao qrCodeDao;

    public JSONObject createTempQrCode(String accessToken, String userId, Integer sceneId) {
        JSONObject weixinResponse = new JSONObject();
        //TODO 判断当前用户是否有在有效期内的二维码 有则直接拼装返回 没有在调用接口生成、

        Map parm = new HashMap();
        parm.put("userId",userId);
        parm.put("time",new Date().getTime());
        List<QRCode> qrcodeList = qrCodeDao.selectQRCodeByUserId(parm);
        if(qrcodeList!=null && !qrcodeList.isEmpty()){
            QRCode qrCode = qrcodeList.get(0);
            weixinResponse.put("url",qrCode.getUrl());
            weixinResponse.put("expire_seconds",qrCode.getExpireSeconds());
            weixinResponse.put("ticket",qrCode.getTicket());
            return weixinResponse;
        }
        String url = new StringBuffer(serviceHost).append("/cgi-bin/qrcode/create?access_token=")
                .append(accessToken).toString();
        Map<String,Map<String,Integer>> sceneMap = new HashMap<String,Map<String,Integer>>();
        Map<String,Integer> sceneIdMap = new HashMap<String, Integer>();
        sceneIdMap.put("scene_id", sceneId);
        sceneMap.put("scene", sceneIdMap);

        JSONObject attentionXml = new JSONObject();
        attentionXml.put("expire_seconds", 2592000);//30天
        attentionXml.put("action_name", "QR_SCENE");
        attentionXml.put("action_info", sceneMap);
        weixinResponse = restTemplate.postForObject(url, attentionXml, JSONObject.class,new HashMap<String,String>());
        logger.debug("创建二维码产生的返回值为P:"+weixinResponse.toString());
        //此url位生成临时二维码url,扫码后可进行公众账号关注
        if(weixinResponse.containsKey("url")){
            String resultUrl = weixinResponse.getString("url");
            if(!StringUtils.isEmpty(resultUrl)){
                SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
                String id = String.valueOf(idWorker.nextId());
                //存入QrCode表中
                Map qrCode= new HashMap();
                qrCode.put("id",id);
                qrCode.put("userId",userId);
                qrCode.put("sceneId",sceneId);
                //String es= rvZeroAndDot(weixinResponse.getString("expire_seconds"));
                qrCode.put("expireSeconds",2592000);//30天
                qrCode.put("ticket",weixinResponse.getString("ticket"));
                qrCode.put("url",resultUrl);
                qrCode.put("createTime",new Date().getTime());
                qrCodeDao.addQRCode(qrCode);
            }
        }else{
            if(weixinResponse.containsKey("errcode")){
                logger.error("创建二维码产生时发生错误:错误码："+weixinResponse.get("errcode")+"错误信息："+weixinResponse.get("errmsg"));
            }
        }
        return weixinResponse;
    }

    private String rvZeroAndDot(String s){
        if (s!=null &&!"".equals(s)) {
            return null;
        }
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
