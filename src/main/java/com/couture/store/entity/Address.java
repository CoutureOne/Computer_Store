package com.couture.store.entity;

/**
 * @author Couture
 * @data: 2022/8/12
 * @description:
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




/** 收货地址数据的实体类*/
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Address extends BaseEntity{
   private Integer aid;
   private Integer uid;
   private String name;
   private String provinceName;
   private String provinceCode;
   private String cityName;
   private String cityCode;
   private String areaCode;
   private String areaName;
   private String zip;
   private String address;
   private String phone;
   private String tel;
   private String tag;
   private Integer isDefault;
}
