package com.heifeng.demo.security.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: xlf
 * @Date: 2021/06/04/11:18
 * @Description:
 */
@ApiModel("所有实体类都需继承此类--公共属性")
@Data
public class BasePojo implements Serializable {

    @TableField
    @ApiModelProperty("删除标识：0为未删除，1为已删除")
    private Integer isDeleted;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField
    @ApiModelProperty("创建时间")
    private Date createTime = new Date();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField
    @ApiModelProperty("最后修改时间")
    private Date updateTime;
}
