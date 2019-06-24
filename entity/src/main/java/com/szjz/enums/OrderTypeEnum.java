package com.szjz.enums;

import lombok.Getter;

/**
 * author:szjz
 * date:2019/6/18
 */

@Getter
public enum OrderTypeEnum {
    APPLY(0, "申购"),
    REDEEM(1, "赎回"),
    ;

    /**
     *
     */
    private Integer code;
    /**
     *
     */
    private String msg;

    public static OrderTypeEnum getByCode(Integer code) {
        for (OrderTypeEnum value : OrderTypeEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        return null;
    }

    OrderTypeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
