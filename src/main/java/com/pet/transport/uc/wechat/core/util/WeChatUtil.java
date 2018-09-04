package com.pet.transport.uc.wechat.core.util;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

public class WeChatUtil {
	private static final String APPID = "wxe6acbca8b05fe976";
	private static final String APPSECRET = "5a75d016855142141434aadc258ce69c";
	private static final String APPTOKEN = "gcloud";
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
     * @param redirectUrl
     * @return
     */
    public String getRequestCodeUrl(String redirectUrl) {
        return String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect",
                             APPID, redirectUrl, "snsapi_userinfo", "xxxx_state");
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
     * 获取APPID
     * @return
     */
    public String getAppToken(){
    	return APPTOKEN;
    }
}
