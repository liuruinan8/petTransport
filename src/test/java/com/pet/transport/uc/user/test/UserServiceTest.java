package com.pet.transport.uc.user.test;

import com.pet.transport.common.test.SpringTestCase;
import com.pet.transport.uc.user.po.User;
import com.pet.transport.uc.user.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashMap;
import java.util.Map;

public class UserServiceTest extends SpringTestCase {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    //@Test
    public void updateUser(){
        Map user = new HashMap();
        user.put("userId","test1");
        user.put("userName","liuRuiNan");
        user.put("userPassword","password");
        userService.updateUser(user);
        System.out.println("update UserUer Success" );
    }
    //@Test
    public void addUser(){
        Map user = new HashMap();
        user.put("userId","test2");
        user.put("userName","wupeiliang");
        user.put("userPassword","password");
        userService.addUser(user);
        System.out.println("AddUer Success" );
    }
    @Test
    public void selectUserByIdTest(){
        User user = userService.selectUserById("oYey51dd_IglORsAzWzGKTgtygPU");
        //System.out.println(user.getUserName() + ":" + user.getUserPassword());
        Assert.assertTrue(user!=null);
    }
}