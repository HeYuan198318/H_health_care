package com.hy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.dao.PermissionDao;
import com.hy.dao.RoleDao;
import com.hy.dao.UserDao;
import com.hy.pojo.Permission;
import com.hy.pojo.Role;
import com.hy.pojo.User;
import com.hy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {

    //注入DAO对象
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDAO;

    public User findByUsername(String username) {
        User user=userDao.findByUsername(username);
        if (user==null){
            return null;
        }else {
            Integer userId = user.getId();
            //根据用户id查询对应的角色 set保证唯一性
            Set<Role> roles=roleDao.findByUserId(userId);
            if (roles!=null && roles.size()>0){
                for (Role role:roles){
                    Integer roleId = role.getId();
                    //根据角色id查询对应的用户权限
                    Set<Permission> permissions = permissionDAO.findByRoleId(roleId);
                    if (permissions!=null && permissions.size()>0) {
                        role.setPermissions(permissions);
                    }
                }
                user.setRoles(roles);
            }
            return user;
        }
    }
}
