package com.hfeng.demo.activiti7.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: xlf
 * @Date: 2021/06/07/10:35
 * @Description:  在Activiti7 官方下载的 Example 中找到 DemoApplicationConfig 类，它的作用是为了实现
 * SpringSecurity 框架的用户权限的配置，这样我们就可以在系统中使用用户权限信息。本次项目中基
 * 本是在文件中定义出来的用户信息，当然也可以是数据库中查询的用户权限信息。
 */
@Configuration
public class DemoApplicationConfig {
    private Logger logger = LoggerFactory.getLogger(DemoApplicationConfig.class);

    /**
     * 用户组的信息，后期嵌入要和数据库关联
     * @return
     */
    @Bean
    public UserDetailsService myUserDetailsService() {
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        String[][] usersGroupsAndRoles = {
                {"zhangsan", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"lisi", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"wangwu", "password", "ROLE_ACTIVITI_USER", "GROUP_activitiTeam"},
                {"salaboy", "password", "ROLE_ACTIVITI_USER", "GROUP_otherTeam"},
                {"system", "password", "ROLE_ACTIVITI_USER"},
                {"admin", "password", "ROLE_ACTIVITI_ADMIN"},
        };
        for (String[] user : usersGroupsAndRoles) {
            List<String> authoritiesStrings = Arrays.asList(Arrays.copyOfRange(user, 2,user.length));
            logger.info("> Registering new user: " + user[0] + " with the following Authorities[" + authoritiesStrings + "]");
                    inMemoryUserDetailsManager.createUser(new User(user[0],
                            passwordEncoder().encode(user[1]),
                            authoritiesStrings.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList())));
        }
        return inMemoryUserDetailsManager;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
