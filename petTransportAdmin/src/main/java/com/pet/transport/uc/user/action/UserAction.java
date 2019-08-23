package com.pet.transport.uc.user.action;

import com.pet.transport.common.contants.URLContants;
import com.pet.transport.uc.shrio.util.CipherUtil;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/uc")
public class UserAction {
    private static Logger logger = LoggerFactory.getLogger(UserAction.class);

    public UserAction() {

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
