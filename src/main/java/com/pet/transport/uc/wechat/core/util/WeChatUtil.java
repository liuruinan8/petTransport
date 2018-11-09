package com.pet.transport.uc.wechat.core.util;

import com.google.gson.Gson;
import com.pet.transport.uc.wechat.core.po.AccessToken;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WeChatUtil {
	private static final String APPID = "wx54d84237cfe4d072";
	private static final String APPSECRET = "cacb74856f39c376655a161ffab56612";
	private static final String APPTOKEN = "airgopet20181106";
	private Log logger = LogFactory.getLog(WeChatUtil.class);
	
	private static WeChatUtil weChatUtil = null;

	public static synchronized WeChatUtil getInstance() {
		if (weChatUtil == null) {
			weChatUtil = new WeChatUtil();
		}
		return weChatUtil;
	}
    /**
     * 生成用于获取access_token的Code的Url
     *
     * @return
     */
    public String getRequestCodeUrl() {
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                APPID, "snsapi_userinfo", "xxxx_state");
    }
	/**
     * 生成用于获取access_token的Code的Url
     *
     * @param redirectUrl
     * @return
     */
    public String getRequestCodeUrl(String redirectUrl) {
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                             APPID, redirectUrl, "snsapi_userinfo", "xxxx_state");
    }
    /**
     * 根据微信id，secret获取access_token
     *
     */
    public String getAccessToken() {
        String tmpUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + APPID
                + "&secret=" + APPSECRET + "";
        CloseableHttpClient httpCilent = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(tmpUrl);
        try {
            HttpResponse httpResponse = httpCilent.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String entity = EntityUtils.toString(httpResponse.getEntity());
                Gson token_gson = new Gson();
                AccessToken accessToken = token_gson.fromJson(entity, AccessToken.class);
                return accessToken.getAccess_token();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                httpCilent.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
	/**
     * 获取请求用户信息的access_token
     *
     * @param code
     * @return
     */
    public Map<String, String> getUserInfoAccessToken(String code) {
    	Map object = null;
        Map<String, String> data = new HashMap();
        try {
            String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code",
                                       APPID, APPSECRET, code);
            logger.info("request accessToken from url: {}"+ url);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String tokens = EntityUtils.toString(httpEntity, "utf-8");
            Gson token_gson = new Gson();
            
            object = token_gson.fromJson(tokens, Map.class);
           
            if(object!=null && object.containsKey("openid")){
            	 logger.info("request accessToken success. [result=]"+ object);
             	data.put("openid", object.get("openid").toString().replaceAll("\"", ""));
                 data.put("access_token", object.get("access_token").toString().replaceAll("\"", ""));
                 data.put("expires_in", object.get("expires_in").toString().replaceAll("\"", ""));
                 data.put("refresh_token", object.get("refresh_token").toString().replaceAll("\"", ""));
            	 
            }else{
            	logger.info("request accessToken false. [result=]"+ object);
            }
            
            
        } catch (Exception ex) {
            logger.error("fail to request wechat access token. [error={}]", ex);
        }
        return data;
    }
    /**
     * 获取用户信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public Map<String, String> getUserInfo(String accessToken, String openId) {
        Map<String, String> data = new HashMap();
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId + "&lang=zh_CN";
        logger.info("request user info from url: {}"+ url);
        Map userInfo = null;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity httpEntity = httpResponse.getEntity();
            String response = EntityUtils.toString(httpEntity, "utf-8");
            Gson token_gson = new Gson();
            userInfo = token_gson.fromJson(response,  Map.class);
            logger.info("get userinfo success. [result={}]"+ userInfo);
            data.put("openid", userInfo.get("openid").toString().replaceAll("\"", ""));
            data.put("nickname", userInfo.get("nickname").toString().replaceAll("\"", ""));
            data.put("city", userInfo.get("city").toString().replaceAll("\"", ""));
            data.put("province", userInfo.get("province").toString().replaceAll("\"", ""));
            data.put("country", userInfo.get("country").toString().replaceAll("\"", ""));
            data.put("headimgurl", userInfo.get("headimgurl").toString().replaceAll("\"", ""));
        } catch (Exception ex) {
            logger.error("fail to request wechat user info. [error={}]", ex);
        }
        return data;
    }
    /**
     * 获取APPID
     * @return
     */
    public String getAppId(){
    	return APPID;
    }
    /**
     * 获取APPTOKEN
     * @return
     */
    public String getAppToken(){
    	return APPTOKEN;
    }

    /**
     * 获取APPSECRET
     * @return
     */
    public String getAppsecret(){
        return APPSECRET;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    public  String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    public  String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
    public  void sort(String a[]) {
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[j].compareTo(a[i]) < 0) {
                    String temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
    }
}
