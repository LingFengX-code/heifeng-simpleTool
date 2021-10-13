package com.heifeng.demo.security.service.impl;

import com.heifeng.demo.security.entity.User;
import com.heifeng.demo.security.service.UserService;
import com.heifeng.demo.security.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: XLF
 * @Date: 2021/07/01/9:44
 * @Description: UserDetailsService的自定义实现
 */
@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    /**
     * 此方法由security过滤器自动调用
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userInfo = userService.findByUsername(username);
        userInfo = userService.findById(userInfo.getId());
        if(userInfo == null){throw new UsernameNotFoundException("【无此用户名！！！】");}
        /*String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
         */
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(userInfo.getUsername(),
                userInfo.getPassword(),
                userInfo.getEnabled() == 1,
                userInfo.getAccountNonExpired() == 1,
                userInfo.getCredentialsNonExpired() == 1,
                userInfo.getAccountNonLocked() == 1,
                SecurityUtil.getAuthorities(userInfo));
        return user;
    }
}
