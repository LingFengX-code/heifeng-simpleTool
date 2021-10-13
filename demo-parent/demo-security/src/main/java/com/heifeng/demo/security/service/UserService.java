package com.heifeng.demo.security.service;

import com.github.pagehelper.PageInfo;
import com.heifeng.demo.security.entity.User;

import java.util.List;

/**
 * @author xiahaha
 */
public interface UserService extends BaseService<User> {

    /**
     * 为用户添加角色信息
     * @param userId 用户id
     * @param roleIds 角色id
     * @return
     */
    Integer addRoleToUser(Long userId, List<Long> roleIds);

    /**
     * 删除指定用户的指定角色
     * @param userId 用户id
     * @param roleId
     * @return
     */
    Integer deleteRoleById(Long userId, Long roleId);

    /**
     * 检查数据是否可使用
     * @param data 用户名/手机号
     * @param type 1为检查用户名/2为检查手机号
     * @return
     */
    public Boolean checkData(String data, Integer type);

    /**
     * 生成验证码并发送
     * @param phone 要发送的手机
     * @return
     */
    public Boolean sendVerifyCode(String phone);

    /**
     * 注册用户
     * @param user 用户信息
     * @param code 注册验证码
     * @return
     */
    public Boolean register(User user, String code);

    /**
     * 判断用户账号密码是否正确
     * @param username
     * @param password
     * @return
     */
    public User queryUser(String username, String password);

    /**
     * 根据组织id分页查询用户
     * @param orgId 组织id
     * @param page
     * @param size
     * @return
     */
    PageInfo listByOrgIdAndPage(Long orgId, Integer page, Integer size);

    /**
     * 根据用户名查询
     * @param username 用户名
     * @return
     */
    User findByUsername(String username);
}
