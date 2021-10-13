package com.heifeng.demo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heifeng.demo.security.entity.Organization;
import com.heifeng.demo.security.entity.Role;
import com.heifeng.demo.security.entity.User;
import com.heifeng.demo.security.entity.UserRole;
import com.heifeng.demo.security.mapper.*;
import com.heifeng.demo.security.service.OrganizationService;
import com.heifeng.demo.security.service.UserService;
import com.heifeng.utils.common.utils.CodecUtils;
import com.heifeng.utils.common.utils.NumberUtils;
import com.heifeng.utils.common.utils.SnowFlakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xlf
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
//    @Autowired
//    private StringRedisTemplate redisTemplate;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private PermissionMapper permissionMapper;
//    @Autowired
//    private AmqpTemplate amqpTemplate;

    static final String KEY_PREFIX = "user:code:phone:";

    static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    /**
     * 为用户添加角色信息
     *
     * @param userId  用户id
     * @param roleIds 角色id
     * @return
     */
    @Override
    public Integer addRoleToUser(Long userId, List<Long> roleIds) {
        //用来进行成功执行数据的计数
        return roleIds.stream().map(roleId ->
                new UserRole(userId, roleId)).map(usersRole -> userRoleMapper.insert(usersRole)).mapToInt(insertOp -> insertOp > 0 ? 1 : 0).sum();
    }

    /**
     * 删除指定用户的指定角色
     *
     * @param userId 用户id
     * @param roleId
     * @return
     */
    @Override
    public Integer deleteRoleById(Long userId, Long roleId) {
        return userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", userId).eq("role_id", roleId));
    }

    /**
     * 检查数据是否可使用
     * @param data 用户名/手机号
     * @param type 1为检查用户名/2为检查手机号
     * @return
     */
    @Override
    public Boolean checkData(String data, Integer type) {
        User record = new User();
        switch (type){
            case 1:
                record.setUsername(data);
                break;
            case 2:
                record.setPhone(data);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(new QueryWrapper<>(record)) > 0;
    }

    /**
     * 生成验证码并发送
     * @param phone 要发送的手机
     * @return
     */
    @Override
    public Boolean sendVerifyCode(String phone) {
        // 生成验证码
        String code = NumberUtils.generateCode(6);
       // code = "iLoveU";
        try {
            // 发送短信
            Map<String, String> msg = new HashMap<String, String>();
            msg.put("phone", phone);
            msg.put("code", code);
            //生产消息
//            this.amqpTemplate.convertAndSend("HEIFENG.SMS.EXCHANGE", "sms.verify.code", msg);
            logger.info("成功发送了MQ消息"+msg);
            // 将code存入redis
//            this.redisTemplate.opsForValue().set(KEY_PREFIX + phone, code, 5, TimeUnit.MINUTES);
            return true;
        } catch (Exception e) {
            logger.error("发送短信失败。phone：{}， code：{}", phone, code);
            return false;
        }
    }

    /**
     * 注册用户
     * @param user 用户信息
     * @param code 注册验证码
     * @return
     */
    @Override
    public Boolean register(User user, String code) {
        // 校验短信验证码
//        String cacheCode = this.redisTemplate.opsForValue().get(KEY_PREFIX + user.getPhone());
//        if (!StringUtils.equals(code, cacheCode)) { return false; }
        boolean bool = this.saveOrUpdate(user) == 1;
        if(bool){
            // 注册成功，删除redis中的记录
//            this.redisTemplate.delete(KEY_PREFIX + user.getPhone());
        }
        return bool;
    }

    /**
     * 判断用户账号密码是否正确
     * @param username
     * @param password
     * @return
     */
    @Override
    public User queryUser(String username, String password) {
        // 查询
        logger.info("要查询的参数为："+username+"  "+password);
        User user = this.userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
        // 校验用户名
        if (user == null) { return null;}
        // 校验密码
        if (!user.getPassword().equals(CodecUtils.md5Hex(password, user.getSalt()))) { return null; }
        // 用户名密码都正确
        return user;
    }

    /**
     * 根据组织id查询所有子组织下的用户信息
     * @param orgId 组织id
     * @return
     */
    private List<User> findAllByOrgPid(Long orgId) {
        //如果orgId不是子组织id，就递归拿到所有子组织id
        List<Organization> organizationList = organizationService.findAllByPid(orgId);
        if(null != organizationList && organizationList.size() > 0){
            List<Long> orgIds = organizationService.getIdsByPid(orgId,new ArrayList<Long>());
            logger.info("找到的所有子组织"+orgIds);
            return userMapper.findAllByOrganIds(orgIds);
        }
        return userMapper.findAllByOrganId(orgId);
    }

    /**
     * 根据组织id分页查询用户
     *
     * @param orgId 组织id
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo listByOrgIdAndPage(Long orgId, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        PageInfo<User> userPageInfo = new PageInfo<>(this.findAllByOrgPid(orgId));
        userPageInfo.getList().forEach(user -> {
            List<Organization> organizationList = organizationMapper.findAllByUserId(user.getId());
            user.setOrganizationList(organizationList);
        });
        return userPageInfo;
    }

    /**
     * 根据用户名查询
     *
     * @param username 用户名
     * @return
     */
    @Override
    public User findByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("is_deleted", 0).eq("username",username));
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectList(new QueryWrapper<User>().eq("is_deleted", 0));
    }

    /**
     * 分页查询
     * @param page  当前页码（默认从1开始）
     * @param pageSize  每页展示条数
     * @return
     */
    @Override
    public PageInfo findAllByPage(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);
        PageInfo<User> userPageInfo = new PageInfo<>(this.findAll());
        userPageInfo.getList().forEach(user -> {
            List<Organization> organizationList = organizationMapper.findAllByUserId(user.getId());
            user.setOrganizationList(organizationList);
        });
        return userPageInfo;
    }


    @Override
    public User findById(Serializable id) {
        //根据id查找的时候，会把多对多关系表的数据写入List里面
        User user = userMapper.selectById(id);
        if(user.getIsDeleted() != 0){return null;}
        //填充角色
        List<Role> roleList = roleMapper.findAllByUserId(user.getId());
        user.setRoleList(roleList);
        roleList.forEach(role -> {
            role.setPermissionList(permissionMapper.findAllByRoleId(role.getId()));
        });
        //填充组织
        List<Organization> organizationList = organizationMapper.findAllByUserId(user.getId());
        user.setOrganizationList(organizationList);
        return user;
    }

    /**
     * 保存/更新
     * @param user 用户信息
     * @return 保存或者更新成功的id
     */
    @Override
    public Long saveOrUpdate(User user) {
        if(null != user.getId() && user.getId()!=0L){
            return this.userMapper.updateById(user) == 1 ? user.getId() : null;
        }
        // 生成盐
        String salt = CodecUtils.generateSalt();
        user.setSalt(salt);
        // 对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        // 强制设置不能指定的参数为null
        user.setId(SnowFlakeUtil.nextId());
        return this.userMapper.insert(user) == 1 ? user.getId() : null;
    }

    /**
     * 删除
     *
     * @param id
     */
    @Override
    public Integer delete(Serializable id) {
        User user = this.findById(id);
        user.setIsDeleted(1);
        Long oldId = this.saveOrUpdate(user);
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
