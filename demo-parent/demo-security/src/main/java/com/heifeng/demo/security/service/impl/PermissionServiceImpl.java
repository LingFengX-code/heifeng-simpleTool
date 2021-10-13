package com.heifeng.demo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heifeng.demo.security.entity.Permission;
import com.heifeng.demo.security.entity.Role;
import com.heifeng.demo.security.mapper.PermissionMapper;
import com.heifeng.demo.security.mapper.RoleMapper;
import com.heifeng.demo.security.service.PermissionService;
import com.heifeng.utils.common.utils.SnowFlakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: XLF
 * @Date: 2021/06/22/17:36
 * @Description:
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    /**
     * 查询所有
     *
     * @return 对象集合
     */
    @Override
    public List<Permission> findAll() {
        return permissionMapper.selectList(new QueryWrapper<Permission>().eq("is_deleted", 0));
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
        return new PageInfo<Permission>(this.findAll());
    }

    /**
     * 根据id查询
     *
     * @param id id
     * @return 对象
     */
    @Override
    public Permission findById(Serializable id) {
        Permission permission = permissionMapper.selectById(id);
        if(null == permission || permission.getIsDeleted() != 0){
            return null;
        }
        //填充角色
        List<Role> roleList = roleMapper.findAllByPermissionId(permission.getId());
        permission.setRoleList(roleList);
        return permission;
    }

    /**
     * 保存新增
     *
     * @param permission
     * @return 主键id
     */
    @Override
    public Long saveOrUpdate(Permission permission) {
        if(null != permission.getId() && permission.getId()!=0L){
            return this.permissionMapper.updateById(permission) == 1 ? permission.getId() : null;
        }
        permission.setId(SnowFlakeUtil.nextId());
        // 添加到数据库
        return this.permissionMapper.insert(permission) == 1 ? permission.getId() : null;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public Integer delete(Serializable id) {
        Permission permission = this.findById(id);
        permission.setIsDeleted(1);
        Long oldId = this.saveOrUpdate(permission);
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
