package com.hy.dao;

import com.github.pagehelper.Page;
import com.hy.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(String queryString);

    public long findCountByCheckItemId(Integer id);

    public void deleteById(Integer id);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public void deleteBatch(List<Integer> ids);

    public List<CheckItem> findAll();
}
