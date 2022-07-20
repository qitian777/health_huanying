package com.tian.backend.controller;

import com.alibaba.excel.EasyExcel;
import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.Result;
import com.tian.common.pojo.OrderSetting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
@Slf4j
@PreAuthorize("hasAuthority('ORDERSETTING')")
public class OrderSettingController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "http://health-provider-consumer/orderSetting";

    /**
     * @Author QiGuang
     * @Description 处理上传的表格
     * @Param
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        List<OrderSetting> list = null;
        try {
            list = EasyExcel.read(excelFile.getInputStream())
                    .head(OrderSetting.class)
                    .sheet()
                    .doReadSync();
            Boolean b = restTemplate.postForObject(url + "/addList", list, Boolean.class);
            if (b!=null&&b){
                return new Result(true,MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
            }
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        } catch (IOException e) {
            log.error("OrderSettingController.upload ERROR:"+e.toString());
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

    /**
     * @Author QiGuang
     * @Description 根据日期查询预约设置数据(获取指定日期所在月份的预约设置数据)
     * @Param
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String date){//参数格式为：2023-03
        try{
            List<Map> list = restTemplate.getForObject(url + "/getOrderSettingByMonth/" + date, List.class);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            log.error("OrderSettingController.getOrderSettingByMonth ERROR:"+e.toString());
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    /**
     * @Author QiGuang
     * @Description 根据指定日期修改可预约人数
     * @Param
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        try{
            System.out.println(orderSetting.getOrderDate()+"--------"+orderSetting.getNumber());
            Boolean b = restTemplate.postForObject(url + "/editNumberByDate", orderSetting, Boolean.class);
            System.out.println(b);
            if (b!=null&&b){
                //预约设置成功
                return new Result(true,MessageConstant.ORDERSETTING_SUCCESS);
            }
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }catch (Exception e){
            log.error("OrderSettingController.editNumberByDate ERROR:"+e.toString());
            //预约设置失败
            return new Result(false,MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
