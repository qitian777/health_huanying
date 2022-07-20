package com.tian.provider.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.Result;
import com.tian.provider.service.IMemberService;
import com.tian.provider.service.IReportService;
import com.tian.provider.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description:    统计报表
 * @Author QiGuang
 * @Date 2022/7/17
 * @Version 1.0
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private IMemberService memberService;
    @Autowired
    private ISetmealService setmealService;
    @Autowired
    private IReportService reportService;

    /**
     * @Author QiGuang
     * @Description 会员数量统计
     * @Param
     */
    @GetMapping("/getMemberReport")
    public Map<String,Object> getMemberReport(){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);//获得当前日期之前12个月的日期

        List<String> list = new ArrayList<>();
        for(int i=0;i<12;i++){
            calendar.add(Calendar.MONTH,1);
            list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
        }

        Map<String,Object> map = new HashMap<>();
        map.put("months",list);

        List<Integer> memberCount = memberService.getMemberCountByMonth(list);
        map.put("memberCount",memberCount);

        return map;
    }

    /**
     * @Author QiGuang
     * @Description 套餐占比统计
     * @Param
     */
    @GetMapping("/getSetmealReport")
    public Map<String, Object> getSetmealReport(){
        return setmealService.getSetmealCount();
    }

    /**
     * @Author QiGuang
     * @Description 获取运营统计数据
     * @Param
     */
    @GetMapping("/getBusinessReportData")
    public Map<String, Object> getBusinessReportData() throws Exception {
            return reportService.getBusinessReport();
    }
}
