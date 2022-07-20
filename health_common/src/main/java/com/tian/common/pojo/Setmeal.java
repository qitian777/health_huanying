package com.tian.common.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

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
@TableName("t_setmeal")
@ApiModel(value = "Setmeal对象", description = "")
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String code;

    private String helpCode;

    @ApiModelProperty("套餐适用性别：0不限 1男 2女")
    private String sex;

    @ApiModelProperty("套餐适用年龄")
    private String age;

    @ApiModelProperty("套餐价格")
    private Float price;

    private String remark;

    private String attention;

    @ApiModelProperty("套餐对应图片存储路径")
    private String img;

    //体检套餐对应的检查组，多对多关系
    @TableField(exist = false)
    private List<CheckGroup> checkGroups;
}
