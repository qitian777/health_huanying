package com.tian.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.common.pojo.Order;
import com.tian.common.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
public interface IOrderService extends IService<Order> {

    Integer submitOrder(Map<String,Object> map);

    Map<String, Object> findById(Integer id);
}
