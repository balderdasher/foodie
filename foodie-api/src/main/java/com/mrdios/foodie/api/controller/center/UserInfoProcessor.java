package com.mrdios.foodie.api.controller.center;

import com.mrdios.foodie.common.constant.Constant;
import com.mrdios.foodie.common.utils.CookieUtils;
import com.mrdios.foodie.common.utils.JacksonUtil;
import com.mrdios.foodie.pojo.Users;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInfoProcessor {
    public UserInfoProcessor() {
    }

    /**
     * 去掉用户敏感信息
     */
    public void clearUserSensitive(Users user) {
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
    public void setUserCookie(Users user, HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.setCookie(request, response, Constant.CookieNames.USER_COOKIE_NAME, JacksonUtil.toJackson(user), true);
    }
}