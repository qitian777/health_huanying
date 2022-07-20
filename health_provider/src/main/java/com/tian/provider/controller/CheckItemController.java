package com.tian.provider.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.entity.Result;
import com.tian.common.pojo.CheckItem;
import com.tian.provider.service.ICheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 检查项
 * @Author QiGuang
 * @Date 2022/7/11
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkItem")
public class CheckItemController {
    @Autowired
    private ICheckItemService checkItemService;

    @PostMapping("/add")
    public Boolean addCheckItem(@RequestBody CheckItem checkItem){
        return checkItemService.save(checkItem);
    }

    /**
     * @Author QiGuang
     * @Description 分页查询
     * @Param
     */
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkItemService.getCheckItemPage(queryPageBean);
    }

    /**
     * @Author QiGuang
     * @Description 删除
     * @Param
     */
    @GetMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        return checkItemService.removeById(id);
    }

    /**
     * @Author QiGuang
     * @Description 编辑
     * @Param
     */
    @RequestMapping("/edit")
    public Boolean edit(@RequestBody CheckItem checkItem){
        return checkItemService.updateById(checkItem);
    }

    /**
     * @Author QiGuang
     * @Description 查询所有
     * @Param
     */
    @GetMapping("/findAll")
    public List<CheckItem> findAll(){
        return checkItemService.list();
    }
}
