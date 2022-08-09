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

    /**
     * 用户登录功能
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 当前匹配的用户数据，如果没有则返回 null
     */
    User login(String username, String password);

    /**
     * 用户登录功能
     *
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    void changePassword(Integer uid, String username,
                        String oldPassword,
                        String newPassword );

    /**
     * 根据用户的id查询用户的数据
     *
     * @param uid 用户 id
     * @return 用户的数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的个人信息
     *
     * @param uid 用户 id
     * @param username 用户名称
     * @param user 用户的数据
     */
    void changeInfo(Integer uid, String username, User user);
}
