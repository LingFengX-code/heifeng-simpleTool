package com.heifeng.demo.security.utils;

import com.heifeng.demo.security.entity.Role;
import com.heifeng.demo.security.entity.User;
import com.heifeng.demo.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: XLF
 * @Date: 2021/06/30/16:58
 * @Description:
 */
@Component
public class SecurityUtil {
    @Autowired
    private UserService userService;

    private static SecurityUtil securityUtil;

    @PostConstruct
    public void init() {
        securityUtil = this;
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public static User getCurrUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if("anonymousUser".equals(principal.toString())){
            //可以抛出异常
//          throw new HfException("未检测到登录用户");
            return null;
        }
        UserDetails user = (UserDetails) principal;
        return securityUtil.userService.findByUsername(user.getUsername());
    }

    /**
     * 通过用户名获取用户拥有权限
     * @param username
     */
    public static Set<SimpleGrantedAuthority> getCurrUserPerms(String username){
        User user = securityUtil.userService.findByUsername(username);
        user = securityUtil.userService.findById(user.getId());
        List<Role> roleList = user.getRoleList();
        if(null == roleList || roleList.size() < 1){
            return null;
        }
        return getAuthorities(user);
    }

    /**
     * 为user对象的Collection<? extends GrantedAuthority> authorities赋值：给用户角色
     * @param userInfo
     * @return
     */
    public static Set<SimpleGrantedAuthority> getAuthorities(User userInfo){
        Set<SimpleGrantedAuthority> authoritySet = new HashSet<>();
        userInfo.getRoleList().forEach(role -> {
            role.getPermissionList().forEach(permission -> {
                authoritySet.add(new SimpleGrantedAuthority(permission.getPermTag()));
            });
        });
        return authoritySet;
    }
}
