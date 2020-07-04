package com.mrdios.foodie.api.controller;

import com.mrdios.foodie.common.bean.ApiResponse;
import com.mrdios.foodie.common.utils.JacksonUtil;
import com.mrdios.foodie.pojo.bo.ShopCartBo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 购物车接口
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Slf4j
@RestController
@RequestMapping("shopcart")
public class ShopCartController {

    @PostMapping("/add")
    @ApiOperation(value = "添加购物车", notes = "添加商品到购物车")
    public ApiResponse<Boolean> addToShopCart(@RequestParam String userId, @RequestBody ShopCartBo shopCartBo,
                                              HttpServletRequest request, HttpServletResponse response) {
        log.info("add goods to shop cart:{}", JacksonUtil.toJackson(shopCartBo));
        // TODO: 2020/7/4 0004 用户已登录时持久化购物车
        return ApiResponse.ok();
    }

    @PostMapping("/del")
    @ApiOperation(value = "删除购物车", notes = "删除购物车中的商品")
    public ApiResponse<Boolean> delFromShopCart(@RequestParam String userId, @RequestParam String itemSpecId,
                                                HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isBlank(itemSpecId)) {
            return ApiResponse.errorMsg("商品不能为空");
        }
        log.info("del goods from shop cart:{}", itemSpecId);
        // TODO: 2020/7/4 0004 用户一登录时同步删除用户购物车中的对应商品
        return ApiResponse.ok();
    }
}
