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
 * 权限类
 * @author xlf
 */
@ApiModel("Permission 权限实体类")
@TableName("tb_permission")
@Data
public class Permission extends BasePojo {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    private Long id;

    @TableField
    @ApiModelProperty("权限名")
    private String permName;

    @TableField
    @ApiModelProperty("有访问权限的url")
    private String url;

    @TableField
    @ApiModelProperty("权限标签")
    private String permTag;

    @TableField
    @ApiModelProperty("权限描述")
    private String permissionDesc;

    @TableField(exist = false)
    @ApiModelProperty(value = "权限对应的所有角色",hidden = true)
    private List<Role> roleList;
}
