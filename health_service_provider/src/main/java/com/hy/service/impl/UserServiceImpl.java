package com.hy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hy.dao.PermissionDao;
import com.hy.dao.RoleDao;
import com.hy.dao.UserDao;
import com.hy.entity.PageResult;
import com.hy.pojo.Permission;
import com.hy.pojo.Role;
import com.hy.pojo.User;
import com.hy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Override
    public PageResult pageQuery(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<User> page = userDao.findByCondition(queryString);

        return new PageResult(page.getTotal(),page.getResult());

    }

    @Override
    public void add(User user, Integer[] roles) {
        userDao.add(user);
        //设置检查组和检查项的多对多的关联关系，操作t_checkgroup_checkitem表
        Integer userId = user.getId();
        this.setUserAndRoles(userId,roles);

    }
    //建立检查组和检查项多对多关系
    private void setUserAndRoles(Integer userId, Integer[] roles) {
        if( roles != null && roles.length > 0){
            for (Integer roleId : roles) {
                Map<String,Integer> map = new HashMap<>();
                map.put("checkgroupId",userId);
                map.put("checkitemId",roleId);
                userDao.setUserAndRoles(map);
            }
        }
    }

    @Override
    public User findRoles() {
        return null;
    }

    @Override
    public void update(User user, Integer[] roles) {

    }

    @Override
    public List<Integer> findByRoleIds(Integer id) {
        return null;
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }



}
