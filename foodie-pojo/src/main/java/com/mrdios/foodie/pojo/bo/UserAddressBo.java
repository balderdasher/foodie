package com.mrdios.foodie.pojo.bo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserAddressBo {
    /**
     * 地址主键id
     */
    private String addressId;

    /**
     * 关联用户id
     */
    @NotBlank(message = "用户不能为空")
    private String userId;

    /**
     * 收件人姓名
     */
    @NotBlank(message = "收件人不能为空")
    @Length(min = 2, max = 12, message = "收件人长度太长")
    private String receiver;

    /**
     * 收件人手机号
     */
    @NotBlank(message = "收件人手机号不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$", message = "收件人手机号格式不正确")
    private String mobile;

    /**
     * 省份
     */
    @NotBlank(message = "省份不能为空")
    private String province;

    /**
     * 城市
     */
    @NotBlank(message = "城市不能为空")
    private String city;

    /**
     * 区县
     */
    @NotBlank(message = "区县不能为空")
    private String district;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空")
    private String detail;
}