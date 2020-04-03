package com.hy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.constant.MessageConstant;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.entity.Result;
import com.hy.pojo.CheckItem;
import com.hy.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 检查项管理
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {

    @Reference//查找服务
    private CheckItemService checkItemService;
    //新增检查项
    @RequestMapping("/add")
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
    public Result add(@RequestBody CheckItem checkItem){
        try{
            checkItemService.add(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    //检查项分页查询
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//权限校验
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult = checkItemService.pageQuery(queryPageBean);
        return pageResult;
    }

    //删除检查项
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            checkItemService.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            //调用服务失败
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    //批量删除--参数是id数组
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @RequestMapping("/deleteByIds")
    public Result deleteByIds(Integer[] ids){
        System.out.println(Arrays.toString(ids));
        List<Integer> is=Arrays.asList(ids);
        try {
            checkItemService.deleteBatch(is);
        }catch (Exception e){
            e.printStackTrace();
            //调用服务失败
           return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            CheckItem checkItem=checkItemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //调用服务失败
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //编辑检查项
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//权限校验
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try{
            checkItemService.edit(checkItem);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
        return  new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }

    //查询所有检查项
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckItem> list = checkItemService.findAll();
            return  new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            //服务调用失败
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

}
