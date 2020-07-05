package com.mrdios.foodie.common.constant;

/**
 * 常量定义类
 *
 * @author CodePorter
 * @date 2020-06-21
 */
public interface Constant {
    /**
     * 分页常量
     */
    interface PageConstant {
        Integer DEFAULT_PAGE_SIZE = 10;
        Integer DEFAULT_SEARCH_PAGE_SIZE = 20;
    }

    /**
     * Cookie名定义
     */
    interface CookieNames {
        String SHOP_CART_COOKIE_NAME = "shopcart";
        String USER_COOKIE_NAME = "user";
    }

    interface PayConstants {
        /**
         * 支付中心微信支付要求提供的ID和密码
         */
        String PAY_CENTER_USERID = "9065384-838578616";
        String PAY_CENTER_PASSWORD = "r402-93r2-3ir0-6792";
        /**
         * 支付成功 -> 支付中心 -> 吃货平台
         * 支付回调地址
         */
        String PAY_NOTIFY_URL = "http://vxnp3t.natappfree.cc/orders/payNotify";

        /**
         * 支付中心的调用地址
         */
        String PAYMENT_URL = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
    }
}
