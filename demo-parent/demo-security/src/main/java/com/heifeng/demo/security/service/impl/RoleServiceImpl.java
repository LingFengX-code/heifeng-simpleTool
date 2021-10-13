package com.heifeng.demo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heifeng.demo.security.entity.Permission;
import com.heifeng.demo.security.entity.Role;
import com.heifeng.demo.security.entity.RolePermission;
import com.heifeng.demo.security.entity.User;
import com.heifeng.demo.security.mapper.PermissionMapper;
import com.heifeng.demo.security.mapper.RoleMapper;
import com.heifeng.demo.security.mapper.RolePermissionMapper;
import com.heifeng.demo.security.mapper.UserMapper;
import com.heifeng.demo.security.service.RoleService;
import com.heifeng.utils.common.utils.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: XLF
 * @Date: 2021/06/22/17:23
 * @Description: 角色业务实现
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    /**
     * 给角色关联权限的信息
     *
     * @param roleId
     * @param permissionIds 权限id的集合
     * @return 执行成功的条数
     */
    @Override
    public Integer addPermissionToRole(Long roleId, List<Long> permissionIds) {
        if(null == permissionIds || permissionIds.size() < 1){ return 0; }
        return permissionIds.stream().mapToInt(permissionId -> rolePermissionMapper.insert(new RolePermission(roleId, permissionId))).sum();
    }

    /**
     * 查询用户下的所有角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<Role> findAllByUserId(Long userId) {
        return roleMapper.findAllByUserId(userId);
    }

    /**
     * 查询所有
     *
     * @return 对象集合
     */
    @Override
    public List<Role> findAll() {
        return roleMapper.selectList(new QueryWrapper<Role>().eq("is_deleted", 0));
    }

    /**
     * 分页展示
     *
     * @param page     当前页码（默认从1开始）
     * @param pageSize 每页展示条数
     * @return
     */
    @Override
    public PageInfo findAllByPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        return new PageInfo<Role>(this.findAll());
    }

    /**
     * 根据id查询
     *
     * @param id id
     * @return 对象
     */
    @Override
    public Role findById(Serializable id) {
        Role role = roleMapper.selectById(id);
        if(null == role || role.getIsDeleted() != 0){return null;}
        //填充用户
        List<User> userList = userMapper.findAllByRoleId(role.getId());
        role.setUserList(userList);
        //填充权限
        List<Permission> permissionList = permissionMapper.findAllByRoleId(role.getId());
        role.setPermissionList(permissionList);
        return role;
    }

    /**
     * 保存新增
     *
     * @param role
     * @return 主键id
     */
    @Override
    public Long saveOrUpdate(Role role) {
        if(null != role.getId() && role.getId()!=0L){
            return this.roleMapper.updateById(role) == 1 ? role.getId() : null;
        }
        role.setId(SnowFlakeUtil.nextId());
        // 添加到数据库
        return this.roleMapper.insert(role) == 1 ? role.getId() : null;
    }

    /**
     * 删除
     * @param id
     */
    @Override
    public Integer delete(Serializable id) {
        Role role = this.findById(id);
        role.setIsDeleted(1);
        Long oldId = this.saveOrUpdate(role);
        return (null!=oldId && oldId > 0) ? 1 : 0;
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public Integer deleteByIds(List<Long> ids) {
        //用来进行成功执行数据的计数
        Integer count = 0;
        for(Long id : ids){
            this.delete(id);
            count++;
        }
        return count;
    }
}
