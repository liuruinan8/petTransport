package com.pet.transport.uc.user.service.impl;


import com.pet.transport.uc.user.dao.UserDao;
import com.pet.transport.uc.user.po.User;
import com.pet.transport.uc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service(value = "userServiceImpl")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User selectUserById(String userId) {
        return userDao.selectUserById(userId);
    }

    public void addUser(Map user) {
        userDao.addUser(user);
    }

    public void updateUser(Map user) {
        userDao.updateUser(user);
    }
}