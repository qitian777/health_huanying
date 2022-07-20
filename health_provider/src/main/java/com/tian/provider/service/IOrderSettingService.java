package com.tian.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
public interface IOrderSettingService extends IService<OrderSetting> {

    List<Map> getOrderSettingByMonth(String date);

    Boolean editNumberByDate(OrderSetting orderSetting);

    OrderSetting getOrderSettingByDate(String orderDate);
}
