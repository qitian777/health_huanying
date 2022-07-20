package com.tian.backend.config.security;

import com.tian.backend.config.security.service.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/16
 * @Version 1.0
 */
// 开启权限注解控制
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailServiceImpl userDetailService;

    /**
     * @Author QiGuang
     * @Description 放行页面
     * @Param
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login.html",
                "/css/**",
                "/js/**",
                "/img/**",
                "plugins/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登录前进行过滤
        http.formLogin()
                .loginPage("/login.html")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/myLogin")
//                .defaultSuccessUrl("/pages/main.html")
                .successForwardUrl("/index")
                .failureUrl("/login.html")
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().csrf().disable();
        http.headers().frameOptions().sameOrigin();
    }

    /**
     * @Author QiGuang
     * @Description 注入加密处理类
     * @Param
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @Author QiGuang
     * @Description配置认证处理器
     * @Param
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    }
}
