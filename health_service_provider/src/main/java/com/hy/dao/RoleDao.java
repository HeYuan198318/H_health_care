package com.hy.dao;

import com.hy.pojo.Role;

import java.util.Set;

public interface RoleDao {
    public Set<Role> findByUserId(Integer userId);
}
