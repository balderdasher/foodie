package com.mrdios.foodie.api.controller;

import com.mrdios.foodie.common.bean.ApiResponse;
import com.mrdios.foodie.common.utils.CookieUtils;
import com.mrdios.foodie.common.utils.JacksonUtil;
import com.mrdios.foodie.common.utils.MD5Utils;
import com.mrdios.foodie.pojo.Users;
import com.mrdios.foodie.pojo.bo.UserBo;
import com.mrdios.foodie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 用户注册登录控制器
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Slf4j
@RestController
@RequestMapping("passport")
public class PassportController {
    private static final String USER_COOKIE_NAME = "user";

    @Autowired
    private UserService userService;


    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return
     */
    @GetMapping("/usernameIsExist")
    public ApiResponse<HttpStatus> isUsernameIsExist(String username) {
        // 0 判断用户名是否为空
        if (StringUtils.isBlank(username)) {
            return ApiResponse.errorMsg("用户名不能为空");
        }
        // 1 判断用户名是否已存在
        boolean usernameExist = userService.isUsernameExist(username);
        if (usernameExist) {
            return ApiResponse.errorMsg("用户名已存在");
        }
        // 2 用户名不存在时返回成功
        return ApiResponse.ok(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ApiResponse<Users> register(@RequestBody UserBo userBo,
                                       HttpServletRequest request,
                                       HttpServletResponse response) {
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPassword = userBo.getConfirmPassword();
        // 0 用户名密码不能为空校验
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return ApiResponse.errorMsg("用户名或密码不能为空");
        }
        // 1 用户名是否存在校验
        if (userService.isUsernameExist(username)) {
            return ApiResponse.errorMsg("用户名已存在");
        }
        // 2 密码长度校验
        if (password.length() < 6) {
            return ApiResponse.errorMsg("密码长度不能少于6位");
        }
        // 3 两次输入密码是否一致
        if (!password.equals(confirmPassword)) {
            return ApiResponse.errorMsg("两次输入密码不一致");
        }
        // 4 创建用户
        Users user = userService.createUser(userBo);
        if (Objects.isNull(user)) {
            return ApiResponse.errorMsg("注册失败");
        }
        clearUserSensitive(user);
        // 5 设置cookie
        setUserCookie(user, request, response);
        return ApiResponse.ok(user);
    }


    @PostMapping("/login")
    public ApiResponse<Users> login(@RequestBody UserBo userBo,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        // 0 判断用户名或者密码是否为空
        if (StringUtils.isBlank(userBo.getUsername()) || StringUtils.isBlank(userBo.getPassword())) {
            return ApiResponse.errorMsg("用户名或密码不能为空");
        }
        // 1 登录
        try {
            userBo.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            return ApiResponse.errorMsg("密码处理异常");
        }
        Users user = userService.getUserForLogin(userBo);
        if (Objects.isNull(user)) {
            return ApiResponse.errorMsg("用户名或密码错误");
        }
        clearUserSensitive(user);
        // 2 设置cookie
        setUserCookie(user, request, response);
        log.info("用户:{}登录成功", user.getUsername());
        return ApiResponse.ok(user);
    }

    @PostMapping("/logout")
    public ApiResponse<HttpStatus> logOut(String userid, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, USER_COOKIE_NAME);
        return ApiResponse.ok(HttpStatus.OK);
    }

    /**
     * 去掉用户敏感信息
     */
    private void clearUserSensitive(Users user) {
        user.setUpdatedTime(null);
        user.setCreatedTime(null);
        user.setPassword(null);
        user.setEmail(null);
        user.setMobile(null);
        user.setRealname(null);
    }

    /**
     * 设置用户cookie
     */
    private void setUserCookie(Users user, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.setCookie(request, response, USER_COOKIE_NAME, JacksonUtil.toJackson(user), true);
    }

}
