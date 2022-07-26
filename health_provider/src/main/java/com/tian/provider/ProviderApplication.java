package com.tian.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/11
 * @Version 1.0
 */
@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }
}
