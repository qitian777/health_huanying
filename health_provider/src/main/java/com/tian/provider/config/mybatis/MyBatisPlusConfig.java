package com.tian.provider.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: mybatis-plus分页配置类
 * @Author QiGuang
 * @Date 2022/7/11
 * @Version 1.0
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.tian.provider.mapper")
public class MyBatisPlusConfig {

    /**
     * @Author QiGuang
     * @Description 分页插件
     * @Param
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor =
                new PaginationInnerInterceptor(DbType.MYSQL);
        paginationInnerInterceptor.setOverflow(true);//溢出后从第1页开始
        //指定数据库类型
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}
