package com.tian.backend.controller;

import com.alibaba.fastjson.JSONArray;
import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.entity.Result;
import com.tian.common.pojo.CheckGroup;
import com.tian.common.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/12
 * @Version 1.0
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {
    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "http://health-provider-consumer/checkGroup";

    /**
     * @Author QiGuang
     * @Description 新增
     * @Param
     */
    @PreAuthorize("hasAuthority('CHECKGROUP_ADD')")
    @RequestMapping("/add")
    public Result add(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds) {
        Boolean b = restTemplate.postForObject(url+"/add/"+ Arrays.toString(checkItemIds), checkGroup, Boolean.class);
        if (b != null && b) {
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        }
        return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
    }

    /**
     * @Author QiGuang
     * @Description 编辑
     * @Param
     */
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckGroup checkGroup, Integer[] checkItemIds){
        Boolean b = restTemplate.postForObject(url+"/edit/"+ Arrays.toString(checkItemIds), checkGroup, Boolean.class);
        if (b != null && b) {
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        }
        return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
    }

    /**
     * @Author QiGuang
     * @Description 分页查询
     * @Param
     */
    @PreAuthorize("hasAuthority('CHECKGROUP_QUERY')")
    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return restTemplate.postForObject(url+"/findPage",queryPageBean,PageResult.class);
    }

    /**
     * @Author QiGuang
     * @Description 根据检查组合id查询对应的所有检查项id
     * @Param
     */
    @GetMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer id){
        List<Integer> list= restTemplate.getForObject(url+"/findCheckItemIdsByCheckGroupId/"+id, List.class);
        return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
    }

    /**
     * @Author QiGuang
     * @Description 删除
     * @Param
     */
    @PreAuthorize("hasAuthority('CHECKGROUP_DELETE')")
    @GetMapping("/delete")
    public Result delete(Integer id){
        Boolean b = restTemplate.getForObject(url + "/delete/" + id, Boolean.class);
        if (b != null && b) {
            return new Result(true, MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        }
        return new Result(false, MessageConstant.DELETE_CHECKGROUP_FAIL);
    }

    /**
     * @Author QiGuang
     * @Description 查询所有
     * @Param
     */
    @PreAuthorize("hasAuthority('SETMEAL_ADD')")
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckGroup> list = restTemplate.getForObject(url + "/findAll", List.class);
        if (list!=null&&list.size()>0){
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        }
        return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
    }
}
