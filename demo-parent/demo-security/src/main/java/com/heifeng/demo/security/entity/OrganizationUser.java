package com.heifeng.demo.security.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 组织用户多对多关系表
 * @author xlf
 */
@ApiModel("OrganizationUser 组织用户多对多关系实体类")
@Data
@TableName("tb_organization_user")
public class OrganizationUser extends BasePojo {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    private Long id;

    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("组织id")
    private Long organizationId;

    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("用户id")
    private Long userId;

}
