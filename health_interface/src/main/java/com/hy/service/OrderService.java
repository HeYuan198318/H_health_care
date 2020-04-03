package com.hy.service;

import com.hy.entity.Result;

import java.util.Map;

public interface OrderService {
    Result order(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
