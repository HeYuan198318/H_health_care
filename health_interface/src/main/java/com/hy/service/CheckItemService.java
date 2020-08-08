package com.hy.service;

import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.CheckItem;

import java.util.List;

//服务接口
public interface CheckItemService {
    public void add(CheckItem checkItem);

    public PageResult pageQuery(QueryPageBean queryPageBean);

    public void deleteById(Integer id);

    public void edit(CheckItem checkItem);

    public CheckItem findById(Integer id);

    public void deleteBatch(List<Integer> ids);

    public List<CheckItem> findAll();
}
