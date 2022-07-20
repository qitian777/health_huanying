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
@TableName("t_check_group")
@ApiModel(value = "CheckGroup对象", description = "")
@ToString
public class CheckGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("助记")
    private String helpCode;

    @ApiModelProperty("适用性别")
    private String sex;

    @ApiModelProperty("介绍")
    private String remark;

    @ApiModelProperty("注意事项")
    private String attention;

    @TableField(exist = false)
    private List<CheckItem> checkItems;//一个检查组合包含多个检查项
}
