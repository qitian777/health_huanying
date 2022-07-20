package com.tian.common.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description:    封装返回结果
 * @Author QiGuang
 * @Date 2022/7/10
 * @Version 1.0
 */
@Data
@NoArgsConstructor
public class Result implements Serializable {
    private boolean flag;   //执行结果，true为执行成功 false为执行失败
    private String message; //返回提示信息，主要用于页面提示信息
    private Object data;    //返回数据

    public Result(boolean flag, String message) {
        super();
        this.flag = flag;
        this.message = message;
    }
    public Result(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }
}
