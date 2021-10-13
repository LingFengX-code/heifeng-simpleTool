package com.heifeng.demo.security.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 * @author xlf
 */
@ApiModel("User 用户实体类")
@TableName("tb_user")
@Data
public class User extends BasePojo {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    private Long id;

    @TableField
    @ApiModelProperty("邮箱")
    private String email;

    @TableField
    @Length(min = 4, max = 30, message = "用户名只能在4~30位之间")
    @ApiModelProperty("用户名")
    private String username;

    @TableField
    @JsonIgnore
    @Length(min = 4, max = 30, message = "密码只能在4~30位之间")
    @ApiModelProperty("密码")
    private String password;

    @TableField
    @ApiModelProperty("姓名")
    private String realName;

    @TableField
    @ApiModelProperty("性别 0男 1女")
    private Integer sex;

    @TableField
    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    @ApiModelProperty("手机号")
    private String phone;

    @TableField
    @ApiModelProperty("家庭住址")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField
    @ApiModelProperty("出生日期")
    private String birthday;

    @TableField
    @JsonIgnore
    @ApiModelProperty("加密盐值")
    private String salt;

    @TableField
    @ApiModelProperty("是否启用  0：未启用 1：启用")
    private int enabled;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField
    @ApiModelProperty("上一次登录时间")
    private Date lastLoginTime;

    @TableField
    @ApiModelProperty("账号是否未过期 0：过期 1：未过期")
    private int accountNonExpired;

    @TableField
    @ApiModelProperty("账号是否未锁定 0：锁定 1：未锁定")
    private int accountNonLocked;

    @TableField
    @ApiModelProperty("凭证是否未过期 0：过期 1：未过期")
    private int credentialsNonExpired;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户对应的角色",hidden = true)
    private List<Role> roleList;

    @TableField(exist = false)
    @ApiModelProperty(value = "用户对应的组织",hidden = true)
    private List<Organization> organizationList;
}
