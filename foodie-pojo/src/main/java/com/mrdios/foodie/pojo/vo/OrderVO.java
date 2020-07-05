package com.mrdios.foodie.pojo.vo;

import lombok.Data;

@Data
public class OrderVO {
    private String orderId;
    private MerchantOrderVo merchantOrderVo;
}