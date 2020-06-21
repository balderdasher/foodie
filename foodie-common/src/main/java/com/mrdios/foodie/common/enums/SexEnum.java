package com.mrdios.foodie.common.enums;

/**
 * 性别枚举
 *
 * @author CodePorter
 * @date 2020-06-20
 */
public enum SexEnum {
    FEMALE(0, "女"),
    MALE(1, "男"),
    SECRET(2, "保密");

    private final int code;
    private final String desc;

    SexEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
