package com.heifeng.demo.security.config;

import com.heifeng.demo.security.entity.Permission;
import com.heifeng.demo.security.service.PermissionService;
import com.heifeng.demo.security.service.impl.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Security 配置
 * @author xlf
 */
@Component
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private PermissionService permissionService;
	@Autowired
	private MyUserDetailsServiceImpl myUserDetailService;

	/**
	 * 用户信息的认证和权限配置 -- 替代<security:authentication-manage>
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //用于查询数据库
		auth.userDetailsService(myUserDetailService)
                //配置加密方式
                .passwordEncoder(this.passwordEncoder());
		//以下是自定义加密方式
//		new PasswordEncoder() {
//			@Override
//			public boolean matches(CharSequence rawPassword, String encodedPassword) {
//				String encode = MD5Util.encode((String) rawPassword);
//				encodedPassword=encodedPassword.replace("\r\n", "");
//				boolean result = encodedPassword.equals(encode);
//				return result;
//			}
//			@Override
//			public String encode(CharSequence rawPassword) {
//				return MD5Util.encode((String) rawPassword);
//			}
//		}
	}

	/**
	 * 配置拦截请求资源 -- 替代<security:http>
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		List<Permission> listPermission = permissionService.findAll();
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http
				.authorizeRequests();
		for (Permission permission : listPermission) {
			authorizeRequests.antMatchers(permission.getUrl()).hasAnyAuthority(permission.getPermTag());
		}
		authorizeRequests.antMatchers("/login").permitAll().antMatchers("/**")
				.fullyAuthenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.and()
				.csrf()
				.disable();
//
//        http.authorizeRequests()                                //授权配置
//                .antMatchers("/login","/login.html").permitAll()  //登录路径放行
//                .anyRequest().authenticated()     //其他路径都要认证之后才能访问
//                .and().formLogin()                              //允许表单登录
//                .loginPage("/login.html")                        //这个就是自定义的登录页面
//                .loginProcessingUrl("/login")                   //告诉框架，现在的登录请求的URL地址是:/login
//                .successForwardUrl("/loginSuccess")             // 设置登陆成功页
//                .successHandler(new MyAuthenticationSuccessHandler())//设置认证成功后，handler的处理器
//                .failureHandler(new MyAuthenticationFailureHandler())//设置认证失败后handler的处理器
//                .and().logout().permitAll()                    //登出路径放行 /logout。这是框架自带的登出请求
//                .and().csrf().disable();                        //关闭跨域伪造检查
//
//        //异常处理
//        http.exceptionHandling()
//                .accessDeniedHandler(new DefaultAccessDeniedHandler ())
//                .authenticationEntryPoint(new MyAuthenticationEntryPoint());//身份认证验证失败配置
//
//        http.rememberMe()
//                .tokenRepository(persistentTokenRepository())	//持久
//                .tokenValiditySeconds(2)	//过期时间单位分钟
//                .userDetailsService(userDetailsService); //用来加载用户认证信息的
	}

    /**
     * 密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        //不加密
//        return NoOpPasswordEncoder.getInstance();
        //加密
        return new BCryptPasswordEncoder();
    }


}
