package com.pet.transport.uc.user.service;

import com.pet.transport.uc.user.po.User;

import java.util.Map;

public interface UserService {
    public User selectUserById(String userId);

    public void addUser(Map user);

    public void updateUser(Map user);
}
