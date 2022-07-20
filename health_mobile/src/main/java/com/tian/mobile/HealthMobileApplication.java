package com.tian.mobile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/14
 * @Version 1.0
 */
@SpringBootApplication(exclude= DataSourceAutoConfiguration.class)
public class HealthMobileApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthMobileApplication.class,args);
    }
}
