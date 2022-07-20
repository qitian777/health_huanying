package com.tian.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tian.common.pojo.Permission;
import com.tian.common.pojo.Role;
import com.tian.common.pojo.User;
import com.tian.provider.mapper.PermissionMapper;
import com.tian.provider.mapper.RoleMapper;
import com.tian.provider.mapper.UserMapper;
import com.tian.provider.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq(StringUtils.isNotBlank(username), "username", username);
        User user = userMapper.selectOne(userWrapper);
        if (user != null) {
            Set<Role> roles = roleMapper.getRolesByUserId(user.getId());
            if (roles != null && roles.size() > 0) {
                for (Role role : roles) {
                    Set<Permission> permissions = permissionMapper.getPerssionByRoleId(role.getId());
                    if (permissions != null && permissions.size() > 0) {
                        role.setPermissions(permissions);
                    }
                }
            }
            user.setRoles(roles);
        }
        return user;
    }
}
