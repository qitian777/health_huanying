package com.tian.common.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@TableName("t_order_setting")
@ApiModel(value = "OrderSetting对象", description = "")
public class OrderSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ExcelProperty("日期")
    @DateTimeFormat("yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("预约设置日期")
    private Date orderDate;

    @ExcelProperty("可预约数量")
    @ApiModelProperty("可预约人数")
    private Integer number;

    @ApiModelProperty("已预约人数")
    private Integer reservations;

    public OrderSetting(Date orderDate, Integer number) {
        this.orderDate = orderDate;
        this.number = number;
    }
}
