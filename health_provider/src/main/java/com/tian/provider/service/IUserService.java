package com.tian.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tian.common.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author QiGuang
 * @since 2022-07-10
 */
public interface IUserService extends IService<User> {

    User getUserByUsername(String username);
}
