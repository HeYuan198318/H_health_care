package com.hy.service;

import com.hy.pojo.User;

public interface UserService {
    //根据username获取用户信息
    public User findByUsername(String username);
}
