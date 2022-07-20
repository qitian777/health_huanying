package com.tian.mobile.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.Result;
import com.tian.common.pojo.Setmeal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/14
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "http://health-provider-consumer/setmeal";

    /**
     * @Author QiGuang
     * @Description 获取所有套餐信息
     * @Param
     */
    @GetMapping("/getAllSetmeal")
    public Result getSetmeal(){
        try{
            List<Setmeal> list = restTemplate.getForObject(url+"/getAllSetmeal",List.class);
            return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS,list);
        }catch (Exception e){
            log.error("SetmealController.getSetmeal ERROR:"+e.toString());
            return new Result(false,MessageConstant.GET_SETMEAL_LIST_FAIL);
        }
    }

    /**
     * @Author QiGuang
     * @Description 根据id查询套餐信息
     * @Param
     */
    @GetMapping("/findById")
    public Result findById(int id){
        try{
            Setmeal setmeal =  restTemplate.getForObject(url+"/findById/"+id,Setmeal.class);
            return new Result(true,MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        }catch (Exception e){
            log.error("SetmealController.findById ERROR:"+e.toString());
            return new Result(false,MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }
}
