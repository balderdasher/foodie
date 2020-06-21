package com.mrdios.foodie.pojo.bo;

import lombok.Data;

/**
 * @author CodePorter
 * @date 2020-06-20
 */
@Data
public class UserBo {
    private String username;
    private String password;
    private String confirmPassword;
}
