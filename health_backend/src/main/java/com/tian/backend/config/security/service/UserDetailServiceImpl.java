package com.tian.backend.config.security.service;

import com.tian.common.pojo.Permission;
import com.tian.common.pojo.Role;
//import com.tian.common.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author QiGuang
 * @Date 2022/7/16
 * @Version 1.0
 */
@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private RestTemplate restTemplate;

    private static final String url = "http://health-provider-consumer/user";

    @Override
    //根据用户名查询用户信息
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.tian.common.pojo.User user=restTemplate.getForObject(url+"/getUser/"+username,com.tian.common.pojo.User.class);
        if(user==null){
            //用户名不存在
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        for(Role role : roles){
            //授予角色
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            Set<Permission> permissions = role.getPermissions();
            for(Permission permission : permissions){
                //授权
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        return new User(username,user.getPassword(),list);
    }
}
