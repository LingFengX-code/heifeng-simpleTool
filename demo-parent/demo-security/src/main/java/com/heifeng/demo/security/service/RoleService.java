package com.heifeng.demo.security.service;

import com.heifeng.demo.security.entity.Role;

import java.util.List;

/**
 * @author xiahaha
 */
public interface RoleService extends BaseService<Role>{
    /**
     * 给角色关联权限的信息
     * @param roleId
     * @param permissionIds 权限id的集合
     * @return 执行成功的次数
     */
    Integer addPermissionToRole(Long roleId, List<Long> permissionIds);

    /**
     * 查询用户下的所有角色
     * @param userId
     * @return
     */
    List<Role> findAllByUserId(Long userId);
}
