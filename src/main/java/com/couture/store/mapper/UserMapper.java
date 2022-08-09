package com.couture.store.mapper;

/**
 * @author Couture
 * @data: 2022/8/5
 * @description:
 */

import com.couture.store.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


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


    /**
     * 根据用户的uid来修改用户密码
     *
     * @param uid 用户的uid
     * @param password 用户输入的新密码
     * @param modifiedUser 表示修改的执行者
     * @param modifiedTime 修改数据的时间
     * @return 返回值受影响的行数
     */
    Integer updatePasswordByUid(@Param("uid") Integer uid,
                                @Param("password") String password,
                                @Param("modifiedUser") String modifiedUser,
                                @Param("modifiedTime") Date modifiedTime);

    /**
     * 根据用户的 id 查询用户的数据
     *
     * @param uid 用户 uid
     * @return 如果找到则返回对象，反之则返回 null
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据
     *
     * @param user 用户的数据
     * @return 返回值受影响的行数
     */
    Integer updateInfoByUid(User user);
}
