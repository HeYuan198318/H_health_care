package com.hy.dao;

import com.github.pagehelper.Page;
import com.hy.pojo.User;

import java.util.Map;

public interface UserDao {
    public User findByUsername(String username);

    //分页查询
    Page<User> findByCondition(String queryString);

    void add(User user);

    //设置权限多对多
    void setUserAndRoles(Map<String, Integer> map);

    User findById(Integer id);
}
