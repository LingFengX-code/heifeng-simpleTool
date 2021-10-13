package com.heifeng.demo.security.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.heifeng.utils.common.utils.SnowFlakeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xlf
 */
@ApiModel("RolePermission 角色权限实体类")
@TableName("tb_role_permission")
@Data
public class RolePermission extends BasePojo {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    private Long id;

    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("角色id")
    private Long roleId;

    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("权限id")
    private Long permissionId;

    public RolePermission(Long roleId, Long permissionId) {
        this.id = SnowFlakeUtil.nextId();
        this.roleId = roleId;
        this.permissionId = permissionId;
    }
}
