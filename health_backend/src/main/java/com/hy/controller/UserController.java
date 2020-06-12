package com.hy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.constant.MessageConstant;
import com.hy.entity.Result;
import com.hy.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://localhost:81/pages/*")
public class UserController {
    //    @Reference
//    private MenuService menuService;
    @Reference
    private UserService userService;

    @RequestMapping("/getUsername")
    public Result getUsername() throws Exception {
        //从spring-security框架中获取user对象,认证后会将用户信息保存在框架提供的上下文对象
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();

//                List<Map> lstMenu = menuService.findMenuByUsername(username);//根据用户名查询对应的菜单列表
//                //返回一个map集合
//                Map<String,Object> map = new HashMap<>();
//                map.put("user",user);
//                map.put("menus",lstMenu);
               return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

//    @RequestMapping("/findAll")
//    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
//        PageResult pageResult = userService.pageQuery(
//                queryPageBean.getCurrentPage(),
//                queryPageBean.getPageSize(),
//                queryPageBean.getQueryString());
//        return pageResult;
//    }
//
//    @RequestMapping("/roles")
//    public com.hy.pojo.User roles(){
//        com.hy.pojo.User user = userService.findRoles();
//        return user;
//    }
//
//    @RequestMapping("/add.do")
//    public Result add(@RequestBody com.hy.pojo.User user, Integer[] roles){
//        try {
//            userService.add(user,roles);
//        } catch (Exception e) {
//            //判断添加数据是否成功,如果失败
//            e.printStackTrace();
//            return new Result(false, MessageConstant.ADD_USER_FAIL);
//        }
//        return new Result(true,MessageConstant.ADD_USER_SUCCESS);
//    }
//
//    @RequestMapping("/findById.do")
//    public Result findById(Integer id){
//        com.hy.pojo.User user = null;
//        try {
//            user = userService.findById(id);
//        } catch (Exception e) {
//            //服务失败
//            e.printStackTrace();
//            return new Result(false,MessageConstant.QUERY_USER_FAIL,user);
//        }
//        return new Result(true,MessageConstant.QUERY_USER_SUCCESS,user);
//    }
//
//    @RequestMapping("/update.do")
//    public Result update(@RequestBody com.hy.pojo.User user, Integer[] roles){
//        try {
//            userService.update(user,roles);
//        } catch (Exception e) {
//            //判断修改数据是否成功,如果失败
//            e.printStackTrace();
//            return new Result(false, MessageConstant.EDIT_USER_FAIL);
//        }
//        return new Result(true,MessageConstant.EDIT_USER_SUCCESS);
//    }
//
//    @RequestMapping("/findByRoleIds.do")
//    public Result findByRoleIds(Integer id){
//        List<Integer> roleIds = null;
//        try {
//            roleIds = userService.findByRoleIds(id);
//        } catch (Exception e) {
//            //服务失败
//            e.printStackTrace();
//            return new Result(false,MessageConstant.QUERY_ROLE_FAIL,roleIds);
//        }
//        return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,roleIds);
//    }

}
