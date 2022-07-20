package com.tian.mobile.controller;

import com.alibaba.fastjson.JSON;
import com.tian.common.constant.MessageConstant;
import com.tian.common.constant.RedisMessageConstant;
import com.tian.common.entity.Result;
import com.tian.common.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "http://health-provider-consumer/member";

    /**
     * @Author QiGuang
     * @Description 使用手机号和验证码登录
     * @Param
     */
    @PostMapping("/login")
    public Result login(HttpServletResponse response, @RequestBody Map map) {
        System.out.println(map.toString());
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        System.out.println("---------"+validateCode);
        // 从Redis中获取缓存的验证码
        String codeInRedis = redisTemplate.opsForValue().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            // 验证码输入错误
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        } else {
            // 验证码输入正确
            // 判断当前用户是否为会员，不是则注册
            Member member = restTemplate.getForObject(url + "/login/"+telephone,Member.class);
            if(member==null) return new Result(false, MessageConstant.BACKEND_ERROR);
            //登录成功
            //写入Cookie，跟踪用户
            Cookie cookie = new Cookie("login_member_telephone", telephone);
            cookie.setPath("/");//路径
            cookie.setMaxAge(60 * 60 * 24 * 30);//有效期30天
            response.addCookie(cookie);
            //保存会员信息到Redis中
            String json = JSON.toJSON(member).toString();
            redisTemplate.opsForValue().set(telephone,json,60*30, TimeUnit.SECONDS);
            return new Result(true, MessageConstant.LOGIN_SUCCESS);
        }
    }
}
