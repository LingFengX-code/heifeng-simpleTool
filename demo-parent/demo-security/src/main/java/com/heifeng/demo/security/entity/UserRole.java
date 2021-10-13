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
import lombok.NoArgsConstructor;

/**
 * 用户角色多对多关系表
 * @author xlf
 */
@ApiModel("UserRole 用户角色多对多关系实体类")
@Data
@NoArgsConstructor
@TableName("tb_user_role")
public class UserRole extends BasePojo {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键")
    private Long id;

    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("用户id")
    private Long userId;

    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("角色id")
    private Long roleId;

    public UserRole(Long userId, Long roleId) {
        this.id = SnowFlakeUtil.nextId();
        this.userId = userId;
        this.roleId = roleId;
    }
}
