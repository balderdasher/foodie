package com.mrdios.foodie.common.enums;

/**
 * @Description: 支付方式 枚举
 */
public enum PayMethodEnum {

    WEIXIN(1, "微信"),
    ALIPAY(2, "支付宝");

    public final Integer type;
    public final String value;

    PayMethodEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }

    public static boolean isInvalidPayType(Integer type) {
        return type == null || (type != WEIXIN.type.intValue() && type != ALIPAY.type.intValue());
    }

}
