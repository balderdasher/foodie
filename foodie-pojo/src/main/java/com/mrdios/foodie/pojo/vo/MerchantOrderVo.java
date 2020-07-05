package com.mrdios.foodie.pojo.vo;

import lombok.Data;

/**
 * 支付中心商户订单信息
 *
 * @author CodePorter
 * @date 2020-07-04
 */
@Data
public class MerchantOrderVo {
    private String merchantOrderId;         // 商户订单号
    private String merchantUserId;          // 商户方的发起用户的用户主键id
    private Integer amount;                 // 实际支付总金额（包含商户所支付的订单费邮费总额）
    private Integer payMethod;              // 支付方式 1:微信   2:支付宝
    private String returnUrl;               // 支付成功后的回调地址（自定义）

}
