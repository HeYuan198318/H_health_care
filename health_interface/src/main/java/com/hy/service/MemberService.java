package com.hy.service;

import com.hy.pojo.Member;

import java.util.List;

public interface MemberService {
    //判断当前用户是否为会员（查询会员表来确定）
    Member findByTelephone(String telephone);

    void add(Member member);

    List<Integer> findCountByMonths(List<String> months);

}
