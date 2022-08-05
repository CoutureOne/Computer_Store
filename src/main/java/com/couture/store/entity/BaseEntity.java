package com.couture.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Couture
 * @data: 2022/8/5
 * @description:
 */

/** 作为实体类的基类型 */
@Data
public class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;
}
