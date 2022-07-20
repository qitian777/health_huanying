package com.tian.provider.controller;

import com.tian.common.pojo.Member;
import com.tian.provider.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/15
 * @Version 1.0
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private IMemberService memberService;
    /**
     * @Author QiGuang
     * @Description 使用手机号和验证码登录
     * @Param
     */
    @GetMapping("/login/{telephone}")
    public Member login(@PathVariable String telephone){
        return memberService.loginOrRegisterAndLogin(telephone);
    }
}
