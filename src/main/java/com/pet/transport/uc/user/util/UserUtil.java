package com.pet.transport.uc.user.util;

import com.pet.transport.uc.user.po.User;
import com.pet.transport.uc.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserUtil {
    @Autowired
    @Qualifier("userServiceImpl")
    private  UserService userService;


    private static UserUtil userUtil = null;
    // 非Controller 注入方式
    @PostConstruct
    public void init() {
        userUtil = this;
        userUtil.userService = this.userService;
    }


    public static synchronized UserUtil getInstance(){
        if(null == userUtil)
            userUtil = new UserUtil();

        return userUtil;
    }

    public  Map getLoginUserMap(){
        Map  map =  new HashMap();
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        if(userId !=null && !"".equals(userId)){
            if(userUtil.userService !=null){
                User user = userUtil.userService.selectUserById(userId);
                if(user != null){
                    String userName = user.getUserName();
                    map.put("userName",userName);
                    String userMobile = user.getUserMobile();
                    map.put("userMobile",userMobile);
                }
            }
            map.put("userId",userId);
        }
        return map;
    }
}
