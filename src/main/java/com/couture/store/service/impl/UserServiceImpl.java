package com.couture.store.service.impl;

import com.couture.store.entity.User;
import com.couture.store.mapper.UserMapper;
import com.couture.store.service.IUserService;
import com.couture.store.service.ex.*;
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

    @Override
    public User login(String username, String password) {

        // 根据用户名称来查询用户的数据是否存在，如果不存在则抛出异常
        User result = userMapper.findByUsername(username);
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }

        // 检测用户的密码是否匹配
        String oldPassword = result.getPassword();
        String salt = result.getSalt();
        String newMd5Password = getMD5Password(password, salt);

        if (!newMd5Password.equals(oldPassword)) {
            throw new PasswordNotMatchException("用户密码错误");
        }

        if (result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        // 调用mapper层的findByUsername 来查询用户数据
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        // 将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新数据时产生未知地异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户信息不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("当前用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateInfoByUid(user);
        if (rows != 1) {
            throw new UpdateException("更新数据时未产生异常");
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
