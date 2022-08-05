package com.couture.store.service;

import com.couture.store.entity.User;

/**
 * @author Couture
 * @data: 2022/8/5
 * @description: 用户模块业务层接口
 */
public interface IUserService {
    /**
     * 用户注册方法
     *
     * @param user 用户的数据对象
     */
    void reg(User user);

}
