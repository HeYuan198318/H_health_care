package com.hy.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hy.constant.MessageConstant;
import com.hy.constant.RedisConstant;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.entity.Result;
import com.hy.pojo.Setmeal;
import com.hy.service.SetmealService;
import com.hy.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * 体检套餐管理
 */

@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    //使用JedisPool操作Redis服务
    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;


    //文件上传
    @RequestMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        System.out.println(imgFile);
        String originalFilename = imgFile.getOriginalFilename();//原始文件名 3bd90d2c-4e82-42a1-a401-882c88b06a1a2.jpg
        System.out.println(originalFilename);
        int index=originalFilename.lastIndexOf(".");
        String extention=originalFilename.substring(index-1);//.jpg 获取文件后缀
        //使用UUID随机产生文件名称，防止同名文件覆盖
        String fileName = UUID.randomUUID().toString() + extention;//FuM1Sa5TtL_ekLsdkYWcf5pyjKGu.jpg
        try {
            //将文件上传到七牛云服务器
            QiniuUtils.upload2Qiniu(imgFile.getBytes(),fileName);
            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }

    }

    //新增套餐
    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal,Integer[] checkgroupIds){
        try {
            setmealService.add(setmeal,checkgroupIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.ADD_SETMEAL_FAIL);
        }
        return new Result(true,MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.pageQuery(queryPageBean);
    }

}
