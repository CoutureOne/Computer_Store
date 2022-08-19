package com.couture.store.mapper;

import com.couture.store.entity.Address;
import org.apache.ibatis.annotations.Insert;

/**
 * @author Couture
 * @data: 2022/8/12
 * @description: 收货地址持久层的接口
 */
public interface AddressMapper {
    /**
     * 插入用户收货地址的数据
     *
     * @param address 收货地址的数据
     * @return 受影响的行数
     */
    Integer inset(Address address);

    /**
     * 根据用户id统计收货地址数量
     *
     * @param uid 用户的id
     * @return 当前用户的收货地址的总数
     */
    Integer countByUid(Integer uid);
}
