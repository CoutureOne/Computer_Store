package com.couture.store.service.impl;

import com.couture.store.entity.User;
import com.couture.store.mapper.UserMapper;
import com.couture.store.service.IUserService;
import com.couture.store.service.ex.InsertException;
import com.couture.store.service.ex.UsernameDuplicatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @author Couture
 * @data: 2022/8/5
 * @description: 用户模块业务层实现类
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        // 判断用户名已经被占用
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if (result != null) {
            throw new UsernameDuplicatedException("用户名已经被占用");
        }

        // 密码的加密处理的实现：MD5
        String oldPassword = user.getPassword();
        // 获取盐值（随机生成一个盐值）
        String salt = UUID.randomUUID().toString().toUpperCase();
        // 将密码和盐值进行加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        // 补全数据，盐值的记录
        user.setSalt(salt);
        // 将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);

        // 补全数据 is_delete设置 0
        user.setIsDelete(0);
        // 补全数据：4个日志信息
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        // 执行注册业务功能的实现（rows = 1）
        Integer rows = userMapper.insert(user);
        if (rows != 1) {
            throw new InsertException("在用户注册过程中产生未知异常");
        }
    }

    private String getMD5Password(String password, String salt) {
        for (int i = 0; i < 3; i++) {
            // MD5加密算法的调用(进行三次加密)
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        // 返回加密以后的密码
        return password;
    }
}
