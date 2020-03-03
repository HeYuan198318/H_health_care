package com.hy.service;

import com.hy.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    void add(List<OrderSetting> data);
    //根据日期设置对应的预约设置数据
    void editNumberByDate(OrderSetting orderSetting);
    //根据月份查询对应的预约设置数据
    List<Map> getOrderSettingByMonth(String date);
}
