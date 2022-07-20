package com.tian.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.common.pojo.Member;
import com.tian.provider.mapper.MemberMapper;
import com.tian.provider.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member findByTelephone(String telephone) {
        QueryWrapper<Member> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number", telephone);
        return memberMapper.selectOne(wrapper);
    }

    @Override
    public Member loginOrRegisterAndLogin(String telephone) {
        Member member = findByTelephone(telephone);
        if (member == null) {
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberMapper.insert(member);
        }
        return member;
    }

    @Override
    public List<Integer> getMemberCountByMonth(List<String> month) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < month.size(); i++) {
            if (i == 0) continue;
            int count = memberMapper.getMemberCountBeforeDate(month.get(i) + "-01");
            list.add(count);
        }
        list.add(memberMapper.getMemberCountBeforeDate(LocalDate.now().toString()));
        return list;
    }
}
