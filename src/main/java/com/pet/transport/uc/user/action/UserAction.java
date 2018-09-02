package com.pet.transport.uc.user.action;

import com.pet.transport.uc.shrio.util.CipherUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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



    @RequestMapping("/login")
    @ResponseBody
    public ModelAndView login(){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");
        mav.addObject("test1","test2");
        return mav;
    }
    @RequestMapping("/checkLogin")
    @ResponseBody
    public String  checkLogin(HttpServletRequest request){
        String result = "login.do";
        // 取得用户名
        String username = request.getParameter("username");
        //取得 密码，并用MD5加密
        String password = CipherUtil.generatePassword(request.getParameter("password"));
        //String password = request.getParameter("password");
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);

        Subject currentUser = SecurityUtils.getSubject();
        try {
            System.out.println("----------------------------");
            if (!currentUser.isAuthenticated()){//使用shiro来验证
                token.setRememberMe(true);
                currentUser.login(token);//验证角色和权限
            }
            System.out.println("result: " + result);
            result = "index";//验证成功
        } catch (Exception e) {
            logger.error(e.getMessage());
            result = "login";//验证失败
        }
        return result;
    }
}
