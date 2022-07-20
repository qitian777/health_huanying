package com.tian.backend.controller;

import com.tian.common.constant.MessageConstant;
import com.tian.common.entity.PageResult;
import com.tian.common.entity.QueryPageBean;
import com.tian.common.entity.Result;
import com.tian.common.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

    private static final String url = "http://health-provider-consumer/checkItem";

    /**
     * @Author QiGuang
     * @Description 添加
     * @Param
     */
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")//权限校验
    @PostMapping("/add")
    public Result addCheckItem(@RequestBody CheckItem checkItem) {
        Boolean b = restTemplate.postForObject(url + "/add", checkItem, Boolean.class);
        if (b != null && b) {
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        }
        return new Result(false, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * @Author QiGuang
     * @Description 分页查询
     * @Param
     */
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")//权限校验
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return restTemplate.postForObject(url + "/findPage", queryPageBean, PageResult.class);
    }

    /**
     * @Author QiGuang
     * @Description 删除
     * @Param
     */
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")//权限校验
    @GetMapping("/delete")
    public Result delete(Integer id){
        Boolean b = restTemplate.getForObject(url + "/delete/" + id, Boolean.class);
        if (b != null && b) {
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }
        return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
    }

    /**
     * @Author QiGuang
     * @Description 编辑
     * @Param
     */
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")//权限校验
    @PostMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        Boolean b = restTemplate.postForObject(url + "/edit", checkItem, Boolean.class);
        if (b != null && b) {
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }
        return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
    }

    /**
     * @Author QiGuang
     * @Description 查询所有
     * @Param
     */
    @PreAuthorize("hasAuthority('CHECKGROUP_EDIT')")
    @GetMapping("/findAll")
    public Result findAll(){
        List<CheckItem> list = restTemplate.getForObject(url + "/findAll", List.class);
        if (list!=null&&list.size()>0){
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }
        return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
    }
}
