package com.mrdios.foodie.service.impl;

import com.mrdios.foodie.common.enums.YesOrNoEnum;
import com.mrdios.foodie.mapper.UserAddressMapper;
import com.mrdios.foodie.pojo.UserAddress;
import com.mrdios.foodie.pojo.bo.UserAddressBo;
import com.mrdios.foodie.service.UserAddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author CodePorter
 * @date 2020-07-04
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private Sid sid;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserAddress> queryUserAddressList(String userId) {
        UserAddress address = new UserAddress();
        address.setUserId(userId);
        return userAddressMapper.select(address);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUserAddress(UserAddressBo address) {
        // 1.当前用户如果不存在收货地址，则新增时设为默认地址
        int isDefault = 0;
        if (CollectionUtils.isEmpty(queryUserAddressList(address.getUserId()))) {
            isDefault = 1;
        }
        UserAddress ua = new UserAddress();
        BeanUtils.copyProperties(address, ua);
        ua.setId(sid.nextShort());
        ua.setIsDefault(isDefault);
        Date now = new Date();
        ua.setCreatedTime(now);
        ua.setUpdatedTime(now);
        userAddressMapper.insert(ua);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserAddress(UserAddressBo address) {
        UserAddress ua = new UserAddress();
        BeanUtils.copyProperties(address, ua);
        ua.setId(address.getAddressId());
        ua.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(ua);
    }

    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);
        userAddressMapper.delete(address);
    }

    @Override
    public void setToDefault(String userId, String addressId) {
        // 更新原来的默认地址为非默认
        UserAddress param = new UserAddress();
        param.setUserId(userId);
        param.setIsDefault(YesOrNoEnum.YES.getCode());
        List<UserAddress> defaults = userAddressMapper.select(param);
        defaults.forEach(userAddress -> {
            userAddress.setIsDefault(YesOrNoEnum.NO.getCode());
            userAddress.setUpdatedTime(new Date());
            userAddressMapper.updateByPrimaryKeySelective(userAddress);
        });
        // 设置新的默认地址
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);
        address.setIsDefault(YesOrNoEnum.YES.getCode());
        address.setUpdatedTime(new Date());
        userAddressMapper.updateByPrimaryKeySelective(address);
    }

    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {
        UserAddress param = new UserAddress();
        param.setId(addressId);
        param.setUserId(userId);
        return userAddressMapper.selectOne(param);
    }
}
