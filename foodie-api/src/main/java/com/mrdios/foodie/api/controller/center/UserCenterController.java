package com.mrdios.foodie.api.controller.center;

import com.mrdios.foodie.common.bean.ApiResponse;
import com.mrdios.foodie.pojo.Users;
import com.mrdios.foodie.service.center.UserCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户中心首页
 *
 * @author CodePorter
 * @date 2020-07-05
 */
@Api(value = "获取用户信息", tags = "用户中心展示相关接口")
@RestController
@RequestMapping("center")
public class UserCenterController {

    @Autowired
    private UserCenterService userCenterService;

    @GetMapping("/userInfo")
    @ApiOperation(value = "获取用户信息", notes = "获取用户信息", httpMethod = "GET")
    public ApiResponse<Users> getUserInfo(String userId) {
        return ApiResponse.ok(userCenterService.queryUserInfo(userId));
    }
}
