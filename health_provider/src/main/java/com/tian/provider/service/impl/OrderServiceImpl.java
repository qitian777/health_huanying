package com.tian.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.common.pojo.Member;
import com.tian.common.pojo.Order;
import com.tian.common.pojo.OrderSetting;
import com.tian.common.utils.DateUtils;
import com.tian.provider.mapper.MemberMapper;
import com.tian.provider.mapper.OrderMapper;
import com.tian.provider.service.IMemberService;
import com.tian.provider.service.IOrderService;
import com.tian.provider.service.IOrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    @Autowired
    private IOrderSettingService orderSettingService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Transactional
    public Integer submitOrder(Map<String,Object> map) {
        //检查当前日期是否进行了预约设置
        String orderDate = (String) map.get("orderDate");
        OrderSetting orderSetting = orderSettingService.getOrderSettingByDate(orderDate);
        if (orderSetting == null) return -1;
        if (orderSetting.getReservations()==null)orderSetting.setReservations(0);
        if (orderSetting.getNumber() <= orderSetting.getReservations()) return -2;
        //检查当前用户是否为会员，根据手机号判断
        String telephone = (String) map.get("telephone");
        QueryWrapper<Member> wrapper=new QueryWrapper<>();
        wrapper.eq("phone_number",telephone);
        Member member = memberMapper.selectOne(wrapper);
        if (member==null){
            //当前用户不是会员，需要添加到会员表
            member = new Member();
            member.setName((String) map.get("name"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberMapper.insert(member);
        }else{
            //当前用户是会员，是否已预约过
            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            Map<String,Object> orderMap=new HashMap<>();
            orderMap.put("member_id",member.getId());
            orderMap.put("order_date",orderDate);
            orderMap.put("setMeal_id",setmealId);
            List<Order> list = orderMapper.selectByMap(orderMap);
            if (list != null && list.size() > 0) {
                //已经完成了预约，不能重复预约
                return -3;
            }
        }

        //可以预约，设置预约人数加一
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingService.updateById(orderSetting);

        try{
            //保存预约信息到预约表
            Order order=new Order(member.getId(),
                    DateUtils.parseString2Date(orderDate),(String)map.get("orderType"),Order.ORDERSTATUS_NO,
                    Integer.parseInt((String) map.get("setmealId")));
            orderMapper.insert(order);
            return order.getId();
        }catch (Exception e){
            log.error("OrderServiceImpl.submitOrder ERROR:"+e.toString());
            return -4;
        }

    }

    @Override
    public Map<String, Object> findById(Integer id) {
        return orderMapper.findById(id);
    }
}
