package com.hy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hy.constant.RedisConstant;
import com.hy.dao.SetmealDao;
import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.Setmeal;
import com.hy.service.SetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    //注入DAO对象
    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${out_put_path}")
    private String outPutPath;

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if (checkgroupIds!=null && checkgroupIds.length>0){
            //绑定套餐和检查组的多对多关系
            Integer setmealId = setmeal.getId();
            this.setSetmealAndCheckGroup(setmealId,checkgroupIds);
        }
        //将图片名称保存到Redis集合中
        String fileName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,fileName);

//        //当添加套餐后需要重新生成静态页面（套餐列表页面、套餐详情页面）
//        generateMobileStaticHtml();

    }

    /**
     * 生成当前添加方法所需的静态页面
     */
    private void generateMobileStaticHtml() {
        //准备模板文件中所需的数据
        List<Setmeal> list = setmealDao.findAll();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml( list );
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml( list );
    }

    /**
     * 生成套餐列表静态页面
     *
     * @param list 页面数据
     */
    private void generateMobileSetmealListHtml(List<Setmeal> list) {
        Map<String, Object> dataMap = new HashMap<>();
        //为模板提供数据，用于生成静态页面
        dataMap.put( "setmealList", list );
        generateHtml( "mobile_setmeal.ftl", "m_setmeal.html", dataMap );
    }

    /**
     * 生成套餐详情静态页面
     *
     * @param list 页面数据
     */
    private void generateMobileSetmealDetailHtml(List<Setmeal> list) {
        for (Setmeal setmeal : list) {
            Map<String, Object> dataMap = new HashMap<>();
            //为模板提供对应id所有数据
            dataMap.put( "setmeal", setmealDao.findById( setmeal.getId() ) );
            generateHtml( "mobile_setmeal_detail.ftl", "setmeal_detail_" + setmeal.getId() + ".html", dataMap );
        }
    }

    /**
     * 通用的方法，用于生成静态页面
     *
     * @param templateName freeMarker模板名
     * @param htmlPageName 生成后的页面名
     * @param dateMap      页面所需数据
     */
    private void generateHtml(String templateName, String htmlPageName, Map<String, Object> dateMap) {
        //获得配置对象
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        BufferedWriter writer = null;
        try {
            //选定生成模板
            Template template = configuration.getTemplate( templateName );
            //生成数据
            File docFile=new File(outPutPath+"\\"+htmlPageName);
            //构造输出流
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            //输出文件
            template.process( dateMap, writer );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //流不为空进行关闭
                Objects.requireNonNull( writer ).close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findByCondition(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    //根据套餐ID查询套餐详情（套餐基本信息、套餐对应的检查组信息、检查组对应的检查项信息）
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    //查询预约套餐所有名字和数量
    public List<Map<String, Object>> findSetmealCount() {
        return setmealDao.findSetmealCount();
    }

    //设置套餐和检查组多对多关系，操作t_setmeal_checkgroup
    public void setSetmealAndCheckGroup(Integer setmealId,Integer[] checkgroupIds){
        if(checkgroupIds != null && checkgroupIds.length > 0){
            for (Integer checkgroupId : checkgroupIds) {
                Map<String,Integer> map = new HashMap<>();
                map.put("setmealId",setmealId);
                map.put("checkgroupId",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }
}
