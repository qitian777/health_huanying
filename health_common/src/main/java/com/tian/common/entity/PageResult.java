package com.tian.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:    分页结果封装对象
 * @Author QiGuang
 * @Date 2022/7/10
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {
    //总记录数
    private Long total;

    //当前页结果
    private List rows;
}
