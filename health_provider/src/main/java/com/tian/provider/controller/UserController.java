package com.tian.provider.controller;

import com.tian.common.pojo.User;
import com.tian.provider.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/16
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/getUser/{username}")
    public User getUser(@PathVariable String username){
        return userService.getUserByUsername(username);
    }
}
