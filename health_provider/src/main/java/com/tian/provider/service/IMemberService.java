package com.tian.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.common.pojo.Member;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
public interface IMemberService extends IService<Member> {

    Member findByTelephone(String telephone);

    Member loginOrRegisterAndLogin(String telephone);

    List<Integer> getMemberCountByMonth(List<String> month);
}
