package com.hy.service;

import com.hy.entity.PageResult;
import com.hy.pojo.User;

import java.util.List;

public interface UserService {
    //根据username获取用户信息
    public User findByUsername(String username);

    PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString);

    void add(User user, Integer[] roles);

    User findRoles();

    //更新用户数据或者权限
    void update(User user, Integer[] roles);

    //根据用户id查询权限id
    List<Integer> findByRoleIds(Integer id);
    //根据id查找用户
    User findById(Integer id);
}
