package com.hy.dao;

import com.hy.pojo.Permission;

import java.util.Set;

public interface PermissionDao {
    public Set<Permission> findByRoleId(Integer roleId);
}
