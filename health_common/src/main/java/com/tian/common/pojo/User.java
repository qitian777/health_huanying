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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
@TableName("t_user")
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("用户名，唯一")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("状态")
    private String station;

    @ApiModelProperty("联系电话")
    private String telephone;

    //对应角色集合
    @TableField(exist = false)
    private Set<Role> roles = new HashSet<Role>(0);
}
