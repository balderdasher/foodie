package com.mrdios.foodie.service.center;

import com.mrdios.foodie.pojo.Users;
import com.mrdios.foodie.pojo.bo.center.CenterUserBo;

/**
 * 用户中心接口
 *
 * @author CodePorter
 * @date 2020-07-05
 */
public interface UserCenterService {
    /**
     * 根据用户id查询用户信息
     *
     * @param userId 用户id
     */
    Users queryUserInfo(String userId);

    /**
     * 修改用户信息
     *
     * @param userId
     * @param users  用户信息
     */
    Users updateUserInfo(String userId, CenterUserBo users);
}
