package com.mrdios.foodie.common.enums;

/**
 * 是否枚举
 *
 * @author CodePorter
 * @date 2020-06-20
 */
public enum YesOrNoEnum {
    YES(1, "是"),
    NO(0, "否");

    private final Integer code;
    private final String desc;

    YesOrNoEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
