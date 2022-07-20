package com.tian.provider.controller;

import com.alibaba.fastjson.JSONArray;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.pojo.CheckGroup;
import com.tian.common.pojo.CheckItem;
import com.tian.provider.service.ICheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 检查组
 * @Author QiGuang
 * @Date 2022/7/11
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {
    @Autowired
    private ICheckGroupService checkGroupService;

    /**
     * @Author QiGuang
     * @Description 新增
     * @Param
     */
    @RequestMapping("/add/{ids}")
    public Boolean add(@RequestBody CheckGroup checkGroup, @PathVariable String ids) {
        List<Integer> list = JSONArray.parseArray(ids, Integer.class);
        return checkGroupService.saveCheckGroup(checkGroup, list);
    }

    /**
     * @Author QiGuang
     * @Description 编辑
     * @Param
     */
    @RequestMapping("/edit/{ids}")
    public Boolean edit(@RequestBody CheckGroup checkGroup, @PathVariable String ids){
        List<Integer> list = JSONArray.parseArray(ids, Integer.class);
        return checkGroupService.updateCheckGroup(checkGroup,list);
    }

    /**
     * @Author QiGuang
     * @Description 分页查询
     * @Param
     */
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkGroupService.getCheckGroupPage(queryPageBean);
    }

    /**
     * @Author QiGuang
     * @Description 根据检查组合id查询对应的所有检查项id
     * @Param
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId/{id}")
    public List<Integer> findCheckItemIdsByCheckGroupId(@PathVariable Integer id){
       return checkGroupService.findCheckItemIdsByCheckGroupId(id);
    }

    /**
     * @Author QiGuang
     * @Description 删除
     * @Param
     */
    @GetMapping("/delete/{id}")
    public Boolean delete(@PathVariable("id") Integer id){
        return checkGroupService.removeCheckGroup(id);
    }

    /**
     * @Author QiGuang
     * @Description 查询所有
     * @Param
     */
    @GetMapping("/findAll")
    public List<CheckGroup> findAll(){
        return checkGroupService.list();
    }
}
