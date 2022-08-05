package com.couture.store.mapper;

/**
 * @author Couture
 * @data: 2022/8/5
 * @description:
 */

import com.couture.store.entity.User;
import org.springframework.stereotype.Repository;


/** 用户模块的持久层接口 */
@Repository
public interface UserMapper {
    /**
     * 插入用户的数据
     *
     * @param user 用户的数据
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户数据
     *
     * @param username 用户名
     * @return 如果找到对应的用户则返回这个用户数据，如果没有找到则返回 null
     */
    User findByUsername(String username);
}
