package com.tian.backend.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/17
 * @Version 1.0
 */
@Controller
public class LoginController {

    @RequestMapping("/index")
    public String index(){
        return "redirect:/pages/main.html";
    }

    //获取当前登录用户的用户名
    @ResponseBody
    @RequestMapping("/user/getUsername")
    public Result getUsername()throws Exception{
        try{
            org.springframework.security.core.userdetails.User user =
                    (org.springframework.security.core.userdetails.User)
                            SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user.getUsername());
        }catch (Exception e){
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }
}
