package com.hy.service;

import com.hy.entity.PageResult;
import com.hy.entity.QueryPageBean;
import com.hy.pojo.CheckGroup;

import java.util.List;

/**
 * 检查组服务接口
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);
    PageResult pageQuery(QueryPageBean queryPageBean);
    CheckGroup findById(Integer id);
    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);
    List<CheckGroup> findAll();
    void edit(CheckGroup checkGroup, Integer[] checkitemIds);
    void deleteGroupById(Integer id);
}
