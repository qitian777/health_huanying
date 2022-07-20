package com.tian.mobile.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.constant.RedisMessageConstant;
import com.tian.common.entity.Result;
import com.tian.common.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/15
 * @Version 1.0
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RestTemplate restTemplate;
    private static final String url = "http://health-provider-consumer/order";

    /**
     * @Author QiGuang
     * @Description 处理订单
     * @Param
     */
    @PostMapping("/submit")
    public Result submitOrder(@RequestBody Map<String, Object> map) {
        String telephone = (String) map.get("telephone");
        //从Redis中获取缓存的验证码，key为手机号+RedisConstant.SENDTYPE_ORDER
        String codeInRedis = redisTemplate.opsForValue().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        //校验手机验证码
        if (codeInRedis == null || !codeInRedis.equals(validateCode)) {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;
        //调用体检预约服务
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            Integer i = restTemplate.postForObject(url + "/submit", map, Integer.class);
            if (i > 0) {
                result = new Result(true, MessageConstant.ORDER_SUCCESS,i);
            }else if (i == -1) {
                // 预约日期不体检
                result = new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            } else if (i == -2) {
                // 预约已满，不能预约
                result = new Result(false, MessageConstant.ORDER_FULL);
            } else if (i == -3) {
                //已经完成了预约，不能重复预约
                result = new Result(false, MessageConstant.HAS_ORDERED);
            } else {
                result = new Result(false, MessageConstant.ORDER_SUBMIT_FAIL);
            }
        } catch (Exception e) {
            log.error("OrderController.submitOrder ERROR:" + e.toString());
            //预约失败
            return new Result(false, MessageConstant.ORDER_SUBMIT_FAIL);
        }
        return result;
    }

    /**
     * @Author QiGuang
     * @Description 根据id查询预约信息，包括套餐信息和会员信息
     * @Param
     */
    @GetMapping("/findById")
    public Result findById(Integer id){
        try{
            Map<String,Object> map = restTemplate.getForObject(url+"/findById/"+id,Map.class);
            //查询预约信息成功
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            //查询预约信息失败
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }
}
