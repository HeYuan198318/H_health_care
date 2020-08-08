package com.hy.dao;

import com.github.pagehelper.Page;
import com.hy.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    public void add(Setmeal setmeal);

    public void setSetmealAndCheckGroup(Map map);

    public Page<Setmeal> findByCondition(String queryString);

    List<Setmeal> findAll();

    Setmeal findById(Integer id);

    List<Map<String, Object>> findSetmealCount();

    void edit(Setmeal setmeal);

    void deleteAssocication(Integer id);

    void deletesetmealById(Integer id);
}
