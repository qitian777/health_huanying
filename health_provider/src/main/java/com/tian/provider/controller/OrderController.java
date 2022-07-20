package com.tian.provider.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.constant.RedisMessageConstant;
import com.tian.common.entity.Result;
import com.tian.common.pojo.Order;
import com.tian.provider.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/15
 * @Version 1.0
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

    /**
     * @Author QiGuang
     * @Description 处理订单
     * @Param
     */
    @PostMapping("/submit")
    public Integer submitOrder(@RequestBody Map<String,Object> map){
        System.out.println(map.toString());
        return orderService.submitOrder(map);
    }

    /**
     * @Author QiGuang
     * @Description 根据id查询预约信息，包括套餐信息和会员信息
     * @Param
     */
    @GetMapping("/findById/{id}")
    public Map<String,Object> findById(@PathVariable Integer id){
        return orderService.findById(id);
    }
}
