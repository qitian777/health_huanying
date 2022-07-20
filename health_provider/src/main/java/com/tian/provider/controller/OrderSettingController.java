package com.tian.provider.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.Result;
import com.tian.common.pojo.OrderSetting;
import com.tian.provider.service.IOrderService;
import com.tian.provider.service.IOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/13
 * @Version 1.0
 */
@RestController
@RequestMapping("/orderSetting")

public class OrderSettingController {
    @Autowired
    private IOrderSettingService orderSettingService;

    /**
     * @Author QiGuang
     * @Description 处理上传的表格
     * @Param
     */
    @PostMapping("/addList")
    public Boolean upload(@RequestBody List<OrderSetting> list) {
        return orderSettingService.saveBatch(list);
    }

    /**
     * @Author QiGuang
     * @Description 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
     * @Param
     */
    @GetMapping("/getOrderSettingByMonth/{date}")
    public List<Map> getOrderSettingByMonth(@PathVariable String date) {//参数格式为：2023-03
        return orderSettingService.getOrderSettingByMonth(date);
    }

    /**
     * @Author QiGuang
     * @Description 根据指定日期修改可预约人数
     * @Param
     */
    @RequestMapping("/editNumberByDate")
    public Boolean editNumberByDate(@RequestBody OrderSetting orderSetting){
        return orderSettingService.editNumberByDate(orderSetting);
    }
}
