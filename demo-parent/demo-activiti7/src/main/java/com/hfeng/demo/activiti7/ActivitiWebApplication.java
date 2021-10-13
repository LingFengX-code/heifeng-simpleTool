package com.hfeng.demo.activiti7;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xlf
 */
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableAsync
@EnableTransactionManagement //启用事务
public class ActivitiWebApplication {
    private Logger logger = LoggerFactory.getLogger(ActivitiWebApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ActivitiWebApplication.class, args);
    }

}