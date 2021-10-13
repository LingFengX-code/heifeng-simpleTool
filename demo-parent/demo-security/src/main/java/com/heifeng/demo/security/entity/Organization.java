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
 * 组织类
 * @author xlf
 */
@ApiModel("Organization 组织实体类")
@TableName("tb_organization")
@Data
public class Organization extends BasePojo {

    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("主键id")
    private Long id;

    @TableField
    @ApiModelProperty("组织名")
    private String name;

    @TableField
    @ApiModelProperty("组织描述")
    private String orgDesc;

    @TableField
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("所属组织id/父id 为null时表示是顶层部门")
    private Long pid;

    @TableField
    @ApiModelProperty("组织级别：1级为最高没有父id（暂时不启用）")
    private Integer level;

    @TableField(exist = false)
    @ApiModelProperty(value="组织下的所有用户",hidden = true)
    private List<User> userList;

    @TableField(exist = false)
    @ApiModelProperty("是否为最小底层组织")
    private Boolean isMinOrg;
}
