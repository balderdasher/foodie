package com.mrdios.foodie.service.center.impl;

import com.mrdios.foodie.mapper.UsersMapper;
import com.mrdios.foodie.pojo.Users;
import com.mrdios.foodie.pojo.bo.center.CenterUserBo;
import com.mrdios.foodie.service.center.UserCenterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author CodePorter
 * @date 2020-07-05
 */
@Service
public class UserCenterServiceImpl implements UserCenterService {
    @Autowired
    private UsersMapper usersMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserInfo(String userId) {
        Users users = usersMapper.selectByPrimaryKey(userId);
        users.setPassword("");
        return users;
    }

    @Override
    public Users updateUserInfo(String userId, CenterUserBo users) {
        Users updateUser = new Users();
        BeanUtils.copyProperties(users, updateUser);
        updateUser.setId(userId);
        updateUser.setUpdatedTime(new Date());
        usersMapper.updateByPrimaryKeySelective(updateUser);
        return queryUserInfo(userId);
    }
}
