package com.heifeng.demo.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heifeng.demo.security.entity.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author xiahaha
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 查询角色下所有权限信息
     * @param roleId
     * @return
     */
    @Select("select * from tb_permission where id in (" +
            " select permission_id from tb_role_permission where role_id = #{roleId})")
    List<Permission> findAllByRoleId(Long roleId);
}
