package com.couture.store.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Couture
 * @data: 2022/8/5
 * @description:
 */
/** 用户的实体类 */
@Data
public class User extends BaseEntity implements Serializable {
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;
}
