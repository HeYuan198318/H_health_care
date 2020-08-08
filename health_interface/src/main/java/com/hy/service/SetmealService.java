package com.hy.service;

import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult pageQuery(QueryPageBean queryPageBean);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String, Object>> findSetmealCount();

    void edit(Setmeal setmeal, Integer[] checkgroupIds);

    void deletesetmealById(Integer id);
}
