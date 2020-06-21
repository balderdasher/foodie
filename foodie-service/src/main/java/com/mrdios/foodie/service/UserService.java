package com.mrdios.foodie.service;

import com.mrdios.foodie.pojo.Users;
import com.mrdios.foodie.pojo.bo.UserBo;

/**
 * @author CodePorter
 * @date 2020-06-20
 */
public interface UserService {
    /**
     * 判断用户名是否存在
     */
    boolean isUsernameExist(String username);

    /**
     * 创建用户
     */
    Users createUser(UserBo userBo);

    /**
     * 查询登录用户
     */
    Users getUserForLogin(UserBo userBo);
}
