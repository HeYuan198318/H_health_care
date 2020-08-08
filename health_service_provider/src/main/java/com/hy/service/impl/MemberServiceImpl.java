package com.hy.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hy.dao.MemberDao;
import com.hy.pojo.Member;
import com.hy.service.MemberService;
import com.hy.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 会员服务
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    //根据手机号查询会员
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    //新增会员
    public void add(Member member) {
        if (member.getPassword() != null) {
            //用户密码加密
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    //根据月份查找会员总数
    @Override
    public List<Integer> findCountByMonths(List<String> months) {
        List<Integer> list = new ArrayList<>();
        for (String m : months) {
            m = m + ".31";//格式:2019.04.31
            Integer count = memberDao.findMemberCountBeforeDate(m);
            list.add(count);
        }
        return list;
    }

}
