package com.tian.provider.controller;

import com.alibaba.fastjson.JSONArray;
import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.entity.Result;
import com.tian.common.pojo.Setmeal;
import com.tian.provider.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/12
 * @Version 1.0
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private ISetmealService setmealService;

    /**
     * @Author QiGuang
     * @Description 新增
     * @Param
     */
    @RequestMapping("/add/{ids}")
    public Boolean add(@RequestBody Setmeal setmeal, @PathVariable String ids){
        List<Integer> checkGroupIds = JSONArray.parseArray(ids, Integer.class);
        return setmealService.saveSetmeal(setmeal,checkGroupIds);
    }

    /**
     * @Author QiGuang
     * @Description 编辑
     * @Param
     */
    @RequestMapping("/edit/{ids}")
    public Boolean edit(@RequestBody Setmeal setmeal, @PathVariable String ids){
        List<Integer> checkGroupIds = JSONArray.parseArray(ids, Integer.class);
        return setmealService.updateSetmeal(setmeal,checkGroupIds);
    }

    /**
     * @Author QiGuang
     * @Description 分页查询
     * @Param
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return setmealService.getCheckGroupPage(queryPageBean);
    }

    /**
     * @Author QiGuang
     * @Description 根据检查套餐id查询对应的所有检查项id
     * @Param
     */
    @GetMapping("/findCheckGroupIdsBySetmealId/{id}")
    public List<Integer> findCheckGroupIdsBySetmealId(@PathVariable Integer id){
        return setmealService.findCheckGroupIdsBySetmealId(id);
    }

    @GetMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        return setmealService.removeSetmeal(id);
    }

    /**
     * @Author QiGuang
     * @Description 获取所有套餐信息
     * @Param
     */
    @GetMapping("/getAllSetmeal")
    public List<Setmeal> getSetmeal(){
        return setmealService.list();
    }

    /**
     * @Author QiGuang
     * @Description 根据id查询套餐信息
     * @Param
     */
    @GetMapping("/findById/{id}")
    public Setmeal findById(@PathVariable int id){
        return setmealService.findById(id);
    }
}
