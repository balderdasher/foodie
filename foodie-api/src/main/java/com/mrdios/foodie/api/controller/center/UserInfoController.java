package com.mrdios.foodie.api.controller.center;

import com.mrdios.foodie.common.bean.ApiResponse;
import com.mrdios.foodie.pojo.Users;
import com.mrdios.foodie.pojo.bo.center.CenterUserBo;
import com.mrdios.foodie.service.center.UserCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 用户信息相关接口
 *
 * @author CodePorter
 * @date 2020-07-05
 */
@Api(value = "用户信息相关接口", tags = "用户信息相关接口")
@RestController
@RequestMapping("userInfo")
public class UserInfoController {

    @Autowired
    private UserCenterService userCenterService;
    @Autowired
    private UserInfoProcessor userInfoProcessor;

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息", notes = "修改用户信息", httpMethod = "POST")
    public ApiResponse<Boolean> getUserInfo(@RequestParam String userId,
                                            @RequestBody @Valid CenterUserBo users, BindingResult result,
                                            HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(userId)) {
            return ApiResponse.errorMsg("用户id不能为空");
        }
        if (result.hasErrors()) {
            return ApiResponse.errorMsg(getError(result));
        }
        Users newUserInfo = userCenterService.updateUserInfo(userId, users);
        userInfoProcessor.clearUserSensitive(newUserInfo);
        // 2 设置cookie
        userInfoProcessor.setUserCookie(newUserInfo, request, response);
        return ApiResponse.ok(true);
    }

    // fail fast
    private String getError(BindingResult result) {
        return result.getFieldErrors().get(0).getDefaultMessage();
    }
}
