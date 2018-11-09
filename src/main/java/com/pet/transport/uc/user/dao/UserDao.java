package com.pet.transport.uc.user.dao;


import com.pet.transport.uc.user.po.User;

import java.util.Map;

public interface UserDao {

    /** * @param userId * @return User */
    public User selectUserById(String userId);

    public void addUser(Map user);

    public void updateUser(Map user);

    /** * @param userId * @return User */
    public User selectUserByOpenId(String openId);
}