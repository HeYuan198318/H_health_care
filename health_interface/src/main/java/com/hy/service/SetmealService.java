package com.hy.service;

import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);
    PageResult pageQuery(QueryPageBean queryPageBean);
    List<Setmeal> findAll();

    Setmeal findById(Integer id);
}
