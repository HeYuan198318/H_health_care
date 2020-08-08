package com.hy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.dao.OrderSettingDao;
import com.hy.pojo.OrderSetting;
import com.hy.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 预约设置服务
 */
@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量导入预约设置数据
    public void add(List<OrderSetting> data) {
        if (data != null && data.size() > 0) {
            for (OrderSetting orderSetting : data) {
                //判断当前日期是否已经进行了预约设置
                long countByOrderDate = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if (countByOrderDate > 0) {
                    //已经进行了预约设置，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                } else {
                    //没有进行预约设置，执行插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }


    }

    //根据日期设置对应的预约设置数据
    public void editNumberByDate(OrderSetting orderSetting) {
        Date orderDate = orderSetting.getOrderDate();
        //根据日期查询是否已经进行了预约设置
        long count = orderSettingDao.findCountByOrderDate(orderDate);
        if (count > 0) {
            //当前日期已经进行了预约设置，需要执行更新操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        } else {
            //当前日期没有就那些预约设置，需要执行插入操作
            orderSettingDao.add(orderSetting);
        }
    }

    //根据月份查询对应的预约设置数据
    public List<Map> getOrderSettingByMonth(String date) {
        String begin = date + "-1";//2020-2-1
        String end = date + "-31";//2020-2-31
        Map<String, String> map = new HashMap<>();
        map.put("begin", begin);
        map.put("end", end);
        //调用DAO，根据日期范围查询预约设置数据
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> result = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (OrderSetting orderSetting : list) {
                Map<String, Object> m = new HashMap<>();
                m.put("date", orderSetting.getOrderDate().getDate());
                m.put("number", orderSetting.getNumber());
                m.put("reservations", orderSetting.getReservations());
                result.add(m);
            }
        }
        return result;
    }
}
