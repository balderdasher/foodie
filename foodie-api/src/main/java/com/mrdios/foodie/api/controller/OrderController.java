package com.mrdios.foodie.api.controller;

import com.mrdios.foodie.common.bean.ApiResponse;
import com.mrdios.foodie.common.enums.OrderStatusEnum;
import com.mrdios.foodie.common.enums.PayMethodEnum;
import com.mrdios.foodie.common.utils.JacksonUtil;
import com.mrdios.foodie.pojo.OrderStatus;
import com.mrdios.foodie.pojo.bo.CreateOrderBo;
import com.mrdios.foodie.pojo.vo.MerchantOrderVo;
import com.mrdios.foodie.pojo.vo.OrderVO;
import com.mrdios.foodie.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mrdios.foodie.common.constant.Constant.PayConstants.*;

/**
 * 首页相关接口
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Slf4j
@RestController
@RequestMapping("orders")
@Api(value = "订单相关接口", tags = "订单相关接口")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    @ApiOperation(value = "用户下单", notes = "用户下单接口")
    public ApiResponse<String> createOrder(@RequestBody CreateOrderBo order, HttpServletRequest request, HttpServletResponse response) {
        log.info("create order:{}", JacksonUtil.toJackson(order));
        if (PayMethodEnum.isInvalidPayType(order.getPayMethod())) {
            return ApiResponse.errorMsg("支付方式错误");
        }
        // 1.创建订单
        OrderVO orderVo = orderService.createOrder(order);
        // TODO: 2020/7/4 0004 移除购物车中已结算的商品、支付中心发起支付
        // 2.移除购物车中已结算的商品
//        CookieUtils.setCookie(request, response, SHOP_CART_COOKIE_NAME, "", true);
        // 3.发送商户订单到支付中心进行保存
        MerchantOrderVo merchantOrderVo = orderVo.getMerchantOrderVo();
        merchantOrderVo.setAmount(1); // 1分钱方便测试
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("imoocUserId", PAY_CENTER_USERID);
        headers.add("password", PAY_CENTER_PASSWORD);
        HttpEntity<MerchantOrderVo> entity = new HttpEntity<>(merchantOrderVo, headers);
        ResponseEntity<ApiResponse> responseEntity = restTemplate.postForEntity(PAYMENT_URL, entity, ApiResponse.class);
        ApiResponse statusCode = responseEntity.getBody();
        if (null == statusCode || 200 != statusCode.getStatus()) {
            return ApiResponse.errorMsg("支付中心订单创建失败,请稍后再试");
        }
        return ApiResponse.ok(orderVo.getOrderId());
    }

    @PostMapping("/payNotify")
    @ApiOperation(value = "支付通知", notes = "支付通知接口")
    public Integer wechatPayNotify(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);
        return HttpStatus.OK.value();
    }

    @PostMapping("/getOrderStatus")
    @ApiOperation(value = "获取订单状态", notes = "获取订单状态接口")
    public ApiResponse<OrderStatus> getOrderStatus(String orderId) {
        return ApiResponse.ok(orderService.queryOrderStatus(orderId));
    }
}
