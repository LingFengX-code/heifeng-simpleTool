package com.heifeng.demo.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heifeng.demo.security.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author xlf
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 查询用户下的所有角色
     * @param userId
     * @return
     */
    @Select("select * from tb_role where id in (" +
            " select role_id from tb_user_role where user_id = #{userId})")
    List<Role> findAllByUserId(Long userId);

    /**
     * 查询权限关联的所有角色
     * @param permissionId
     * @return
     */
    @Select("select * from tb_role where id in (" +
            " select role_id from tb_role_permission where permission_id = #{permissionId})")
    List<Role> findAllByPermissionId(Long permissionId);
}
