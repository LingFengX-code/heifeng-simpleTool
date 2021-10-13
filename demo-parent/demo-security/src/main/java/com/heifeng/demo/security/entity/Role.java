package com.heifeng.demo.security.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色
 * @author xlf
 */
@ApiModel("Role 角色实体类")
@TableName("tb_role")
@Data
public class Role extends BasePojo {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    private Long id;

    @TableField
    @ApiModelProperty("角色名")
    private String name;

    @TableField
    @ApiModelProperty("角色描述")
    private String roleDesc;

    @TableField(exist = false)
    @ApiModelProperty(value="角色对应的用户",hidden = true)
    private List<User> userList;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色对应的权限",hidden = true)
    private List<Permission> permissionList;
}
