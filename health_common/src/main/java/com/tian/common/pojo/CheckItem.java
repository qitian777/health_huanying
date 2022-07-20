package com.tian.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

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
@ToString
@TableName("t_check_item")
@ApiModel(value = "CheckItem对象", description = "")
public class CheckItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("项目编码")
    private String code;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("适用性别")
    private String sex;

    @ApiModelProperty("适用年龄（范围），例如：20-50")
    private String age;

    @ApiModelProperty("价格")
    private Float price;

    @ApiModelProperty("检查项类型，分为检查和检验两种类型")
    private String type;

    @ApiModelProperty("注意事项")
    private String attention;

    @ApiModelProperty("项目说明")
    private String remark;

}
