package com.tian.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.common.pojo.OrderSetting;
import com.tian.provider.mapper.OrderSettingMapper;
import com.tian.provider.service.IOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Service
@Transactional
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OrderSetting> implements IOrderSettingService {
    @Autowired
    private OrderSettingMapper orderSettingMapper;

    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        String dateBegin = date + "-1";
        String dateEnd = date + "-31";
        QueryWrapper<OrderSetting> wrapper = new QueryWrapper<>();
        wrapper.between("order_date", dateBegin, dateEnd);
        List<OrderSetting> list = orderSettingMapper.selectList(wrapper);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map orderSettingMap = new HashMap();
            orderSettingMap.put("date", orderSetting.getOrderDate().getDate());//获得日期（几号）
            orderSettingMap.put("number", orderSetting.getNumber());//可预约人数
            if (orderSetting.getReservations() == null) orderSetting.setReservations(0);
            orderSettingMap.put("reservations", orderSetting.getReservations());//已预约人数
            data.add(orderSettingMap);
        }
        return data;
    }

    @Override
    public Boolean editNumberByDate(OrderSetting orderSetting) {
        QueryWrapper<OrderSetting> wrapper = new QueryWrapper<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(orderSetting.getOrderDate());
        wrapper.eq("order_date", str);
        Long count = orderSettingMapper.selectCount(wrapper);
        if (count > 0) {
            return orderSettingMapper.update(orderSetting, wrapper) > 0;
        }
        return orderSettingMapper.insert(orderSetting) > 0;
    }

    @Override
    public OrderSetting getOrderSettingByDate(String orderDate) {
        QueryWrapper<OrderSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("order_date", orderDate);
        return orderSettingMapper.selectOne(wrapper);
    }
}
