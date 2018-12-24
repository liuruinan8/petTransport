package com.pet.transport.uc.user.action;

import com.pet.transport.common.contants.URLContants;
import com.pet.transport.uc.shrio.util.CipherUtil;
import com.pet.transport.uc.user.util.UserUtil;
import com.pet.transport.uc.wechat.core.util.WeChatUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/uc")
public class UserAction {
    private static Logger logger = LoggerFactory.getLogger(UserAction.class);
    private WeChatUtil weChatUtil = WeChatUtil.getInstance();

    public UserAction() {

    }
    /**
     * 微信用户授权
     * @Title: wechatOauth
     * @param @param request
     * @param @param response
     * @param @param model
     * @param @return
     * @return String
     */
    @RequestMapping("/wechatOauth")
    public String wechatOauth(HttpServletRequest request,HttpServletResponse response)  {
        /**
         *  1 第一步：用户同意授权，获取code
         */
        //首先拿到微信公众号的AppID、AppSecret等参数
        //如果用户授权成功则跳转到此url
        String loginUrl = URLContants.WEB_URL+"/uc/wxLogin";
        //用户授权，获取code
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
                + "appid="+weChatUtil.getAppId()+""
                + "&redirect_uri="+loginUrl+""
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=123#wechat_redirect";
        //forward redirect
        return "redirect:"+url+"";
    }
    /**
     * 微信回调地址
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/wxLogin")
    @ResponseBody
    public ModelAndView weiXinLoginGetAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String resultMV = "/uc/error500";
        String code = request.getParameter("code");
        //第一步 通过code 获取access_token 请求地址：https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        Map<String, String> data = new HashMap();
        Map<String, String> result = weChatUtil.getUserInfoAccessToken(code);//通过这个code获取access_token
        if (result != null && result.containsKey("openid")) {
            String openId = result.get("openid");
            if (!StringUtils.isEmpty(openId)) {
                logger.info("try getting user info. [openid={}]" + openId);
                //第二步 通过openId获取我方数据库中是否有对应的userId 如果使用用户名跳转
                Map userMap = UserUtil.getInstance().getUserMapByOpenId(openId);
                if (!(userMap != null && userMap.containsKey("userId"))) {
                    userMap = new HashMap();
                    //如果没有获取信息 开始注册
                    String accessToken = result.get("access_token");
                    String expiresIn = result.get("expires_in");
                    String refreshToken = result.get("refresh_token");
                    //第三步 通过openId 获取userInfo
                    Map<String, String> userInfo = weChatUtil.getUserInfo(accessToken, openId);
                    //使用access_token获取用户信息
                    /**
                     * [result={
                     "openid":"oN9UryuC0Y01aQt0jKxZXbfe658w",
                     "nickname":"lovebread",
                     "sex":1,
                     "language":"zh_CN",
                     "city":"",
                     "province":"",
                     "country":"中国",
                     "headimgurl":"http://wx.qlogo.cn/mmopen/bRLXzTf2f6HNfBTd72heAA7vNKsGKvK3dfreewrewsPff9OaMWib0GibbA8daQmNQvQhagtiaicf4vNC5nYU3ia821QQ/0",
                     "privilege":[]}]
                     */
                    /**
                     * #{userId}, #{userName}, #{userPassword}, #{userMobile}, #{appId}, #{accessToken}, #{expiresIn}, #{createTime}, #{refreshToken}, #{subscribe}, #{subscribeTime}, #{nickname}
                     *         , #{sex}, #{country}, #{province}, #{city}, #{language}, #{headImgUrl}, #{isCertified}, #{openId}
                     */
                    userMap.put("userId", userInfo.get("openid"));
                    userMap.put("userName", userInfo.get("nickname"));
                    //取得 密码，并用MD5加密
                    String password = CipherUtil.generatePassword("YU3ia821QQ");
                    userMap.put("userPassword", password);
                    //TODO 需要手機和短信驗證
                    userMap.put("userMobile", "");

                    userMap.put("appId", "");
                    userMap.put("accessToken", accessToken);
                    userMap.put("expiresIn", expiresIn);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(new Date());
                    userMap.put("createTime", dateString);
                    userMap.put("refreshToken", refreshToken);
                    userMap.put("subscribe", "");
                    userMap.put("subscribeTime", "");

                    userMap.put("nickname", userInfo.get("nickname"));
                    userMap.put("sex", userInfo.get("sex"));
                    userMap.put("country", userInfo.get("country"));
                    userMap.put("province", userInfo.get("province"));
                    userMap.put("city", userInfo.get("city"));
                    userMap.put("language", userInfo.get("language"));
                    userMap.put("headImgUrl", userInfo.get("headImgUrl"));
                    userMap.put("isCertified", "");
                    userMap.put("openId", userInfo.get("openid"));
                    UserUtil.getInstance().addUserByMap(userMap);
                }
                //跳转首页
                String username = (String) userMap.get("userId");
                String password = (String) userMap.get("userPassword");
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);

                Subject currentUser = SecurityUtils.getSubject();
                if (!currentUser.isAuthenticated()) {//使用shiro来验证
                    token.setRememberMe(true);
                    currentUser.login(token);//验证角色和权限
                }

                SavedRequest savedRequest = WebUtils.getSavedRequest(request);
                // 获取保存的URL
                if (savedRequest == null || savedRequest.getRequestUrl() == null) {
                    resultMV = "redirect:/index";//验证成功
                }
                String url=savedRequest.getRequestUrl();
                if(url.indexOf("pet")>-1){
                    url = url.replace("/pet/","/");
                }
                resultMV ="redirect:" + url;
            }

        }
        return new ModelAndView(resultMV);
    }

    @RequestMapping(value = "/login", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        return mav;
    }

    @RequestMapping(value = "/checkLogin", produces = {"text/html;charset=UTF-8;"})
    @ResponseBody
    public ModelAndView checkLogin(HttpServletRequest request) {

        String result = "/uc/login";
        ModelAndView mv = new ModelAndView(result);
        // 取得用户名
        String username = request.getParameter("username");
        //取得 密码，并用MD5加密
        String password = CipherUtil.generatePassword(request.getParameter("password"));
        //String password = request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        Subject currentUser = SecurityUtils.getSubject();
        try {
            if (!currentUser.isAuthenticated()) {//使用shiro来验证
                token.setRememberMe(true);
                currentUser.login(token);//验证角色和权限
            }
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            // 获取保存的URL
            if (savedRequest == null || savedRequest.getRequestUrl() == null) {
                result = "redirect:/index";//验证成功
            }
            logger.debug("RequestUrl:"+savedRequest.getRequestUrl()+"  RequestUri:"+savedRequest.getRequestURI());
            result ="redirect:" + savedRequest.getRequestUrl().replaceAll(URLContants.WEB_ROOT_URL,"");
            return new ModelAndView(result);
        } catch (UnknownAccountException e) {
            logger.error(e.getMessage());
            mv.addObject("errorMsg", "账户不存在");
            return mv;
        } catch (IncorrectCredentialsException e) {
            mv.addObject("errorMsg", "用户名/密码错误");
            return new ModelAndView(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            mv.addObject("errorMsg", "用户名/密码错误");
            result = "/uc/login";//验证失败
            return new ModelAndView(result);
        }
    }
}
