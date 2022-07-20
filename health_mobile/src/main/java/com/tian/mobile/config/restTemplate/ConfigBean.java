package com.tian.mobile.config.restTemplate;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/11
 * @Version 1.0
 */
@Configuration
public class ConfigBean {
    @Bean
    @LoadBalanced //支持负载均衡
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
