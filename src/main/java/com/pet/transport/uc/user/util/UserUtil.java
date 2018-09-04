package com.pet.transport.uc.user.util;

import com.pet.transport.uc.user.po.User;
import com.pet.transport.uc.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

public class UserUtil {
    private static UserUtil userUtil = null;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    public static synchronized UserUtil getInstance(){
        if(null == userUtil)
            userUtil = new UserUtil();

        return userUtil;
    }

    public Map getLoginUserMap(){
        Map  map =  new HashMap();
        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        if(userId !=null && !"".equals(userId)){
            User user = userService.selectUserById(userId);
            if(user != null){
                String userName = user.getUserName();

                map.put("userName",userName);
            }

            map.put("userId",userId);
        }
        return map;
    }
}
