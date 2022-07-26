package com.tian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/11
 * @Version 1.0
 */
@SpringBootApplication
@EnableEurekaServer //开启eureka服务器
public class EurekaServer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServer.class, args);
    }
}
