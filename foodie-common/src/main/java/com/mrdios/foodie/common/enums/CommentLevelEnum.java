package com.mrdios.foodie.common.enums;

/**
 * 商品评价级别枚举
 *
 * @author CodePorter
 * @date 2020-06-21
 */
public enum CommentLevelEnum {
    GOOD(1, "好评"),
    NORMAL(2, "中评"),
    BAD(3, "差评");

    public final Integer code;
    public final String desc;

    CommentLevelEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
