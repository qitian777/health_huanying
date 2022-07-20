package com.tian.mobile.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.constant.RedisMessageConstant;
import com.tian.common.entity.Result;
import com.tian.common.utils.DysmsUtils;
import com.tian.common.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 短信验证码
 * @Author QiGuang
 * @Date 2022/7/15
 * @Version 1.0
 */
@RestController
@RequestMapping("/validateCode")
@Slf4j
public class ValidateCodeController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //体检预约时发送手机验证码
    @GetMapping("/sendOrder")
    public Result sendOrder(String telephone) {
        Integer code = ValidateCodeUtils.generateValidateCode(4);//生成4位数字验证码
        try {
            //发送短信
            int i = DysmsUtils.sendCode(telephone, String.valueOf(code));
            if (i != 200) {
                return new Result(false, MessageConstant.SEND_VALIDATECODE_ERROR);
            }
        } catch (Exception e) {
            log.error("ValidateCodeController.sendOrder ERROR:" + e.toString());
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        // System.out.println("发送的手机验证码为：" + code);
        //将生成的验证码缓存到redis
        redisTemplate.opsForValue().set(telephone + RedisMessageConstant.SENDTYPE_ORDER, String.valueOf(code), 5 * 60, TimeUnit.SECONDS);
        //验证码发送成功
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }

    /**
     * @Author QiGuang
     * @Description 手机快速登录时发送手机验证码
     * @Param
     */
    @GetMapping("/sendLogin")
    public Result sendLogin(String telephone) {
        Integer code = ValidateCodeUtils.generateValidateCode(6);//生成6位数字验证码
        try {
            //发送短信
            int i = DysmsUtils.sendCode(telephone, String.valueOf(code));
            if (i != 200) {
                return new Result(false, MessageConstant.SEND_VALIDATECODE_ERROR);
            }
        } catch (Exception e) {
            log.error("ValidateCodeController.sendLogin ERROR:" + e.toString());
            //验证码发送失败
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
        System.out.println("发送的手机验证码为：" + code);
        //将生成的验证码缓存到redis
        redisTemplate.opsForValue().set(telephone + RedisMessageConstant.SENDTYPE_LOGIN, String.valueOf(code), 5 * 60, TimeUnit.SECONDS);
        //验证码发送成功
        return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
    }
}

