package com.hy.dao;

import com.hy.pojo.User;

public interface UserDao {
    public User findByUsername(String username);
}
