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
@ToString
@TableName("t_member")
@ApiModel(value = "Member对象", description = "")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("档案号")
    private String fileNumber;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private String sex;

    @ApiModelProperty("身份证号")
    private String idCard;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("注册时间")
    private Date regTime;

    @ApiModelProperty("登录密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("出生日期")
    private Date birthday;

    @ApiModelProperty("备注")
    private String remark;


}
