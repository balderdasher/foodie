package com.mrdios.foodie.api.controller;

import com.mrdios.foodie.common.bean.ApiResponse;
import com.mrdios.foodie.pojo.UserAddress;
import com.mrdios.foodie.pojo.bo.UserAddressBo;
import com.mrdios.foodie.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车接口
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Slf4j
@RestController
@RequestMapping("address")
@Api(value = "收货地址", tags = "收货地址相关接口")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @PostMapping("/list")
    @ApiOperation(value = "查询用户收货地址", notes = "查询用户收货地址列表")
    public ApiResponse<List<UserAddress>> getAddressList(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return ApiResponse.ok();
        }
        return ApiResponse.ok(userAddressService.queryUserAddressList(userId));
    }

    @PostMapping("/add")
    @ApiOperation(value = "新增收货地址", notes = "新增收货地址")
    public ApiResponse<Boolean> addUserAddress(@RequestBody @Validated UserAddressBo userAddressBo) {
        userAddressService.addUserAddress(userAddressBo);
        return ApiResponse.ok();
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改收货地址", notes = "修改收货地址")
    public ApiResponse<Boolean> updateUserAddress(@RequestBody @Validated UserAddressBo userAddressBo) {
        if (StringUtils.isBlank(userAddressBo.getAddressId())) {
            return ApiResponse.errorMsg("id不能为空");
        }
        userAddressService.updateUserAddress(userAddressBo);
        return ApiResponse.ok();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "删除收货地址", notes = "删除收货地址")
    public ApiResponse<Boolean> deleteUserAddress(String userId, String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ApiResponse.errorMsg("参数不能为空");
        }
        userAddressService.deleteUserAddress(userId, addressId);
        return ApiResponse.ok();
    }

    @PostMapping("/setDefault")
    @ApiOperation(value = "设置为默认收货地址", notes = "设置为默认收货地址")
    public ApiResponse<Boolean> setToDefault(String userId, String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return ApiResponse.errorMsg("参数不能为空");
        }
        userAddressService.setToDefault(userId, addressId);
        return ApiResponse.ok();
    }
}
