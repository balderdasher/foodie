package com.mrdios.foodie.service;

import com.mrdios.foodie.pojo.UserAddress;
import com.mrdios.foodie.pojo.bo.UserAddressBo;

import java.util.List;

/**
 * @author CodePorter
 * @date 2020-06-13
 */
public interface UserAddressService {
    /**
     * 查询用户收货地址列表
     *
     * @param userId 用户id
     * @return 收货地址列表
     */
    List<UserAddress> queryUserAddressList(String userId);

    /**
     * 新增用户收货地址
     *
     * @param address 收货地址
     */
    void addUserAddress(UserAddressBo address);

    /**
     * 修改用户收货地址
     *
     * @param address 收货地址
     */
    void updateUserAddress(UserAddressBo address);

    /**
     * 删除用户收货地址
     *
     * @param userId    用户id
     * @param addressId 收货地址id
     */
    void deleteUserAddress(String userId, String addressId);

    /**
     * 设置默认收货地址
     *
     * @param userId    用户id
     * @param addressId 收货地址id
     */
    void setToDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id查询地址信息
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    UserAddress queryUserAddress(String userId, String addressId);
}
