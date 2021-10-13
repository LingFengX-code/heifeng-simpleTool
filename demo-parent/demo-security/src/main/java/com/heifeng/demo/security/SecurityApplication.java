package com.heifeng.demo.security;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xlf
 */
@SpringBootApplication
@MapperScan("com.heifeng.demo.security.mapper")
public class SecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
		// Security 两种模式 fromLogin 表单提交认证模式 httpBasic 浏览器与服务器做认证授权
	}

}
