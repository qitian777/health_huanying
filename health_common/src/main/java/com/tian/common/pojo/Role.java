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
import java.util.HashSet;
import java.util.LinkedHashSet;
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
@TableName("t_role")
@ApiModel(value = "Role对象", description = "")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色名称")
    private String name;

    @ApiModelProperty("角色关键字，用于权限控制")
    private String keyword;

    @ApiModelProperty("描述")
    private String description;

    @TableField(exist = false)
    private Set<User> users = new HashSet<User>(0);

    @TableField(exist = false)
    private Set<Permission> permissions = new HashSet<Permission>(0);

    @TableField(exist = false)
    private LinkedHashSet<Menu> menus = new LinkedHashSet<Menu>(0);
}
