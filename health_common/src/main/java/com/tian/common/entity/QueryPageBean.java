package com.tian.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 封装查询条件
 * @Author QiGuang
 * @Date 2022/7/10
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryPageBean implements Serializable {
    //页码
    private Integer currentPage;

    //每页记录数
    private Integer pageSize;

    //查询条件
    private String queryString;
}
