package com.szjz.enums;

import lombok.Getter;

/**
 * author:szjz
 * date:2019/6/18
 */

@Getter
public enum OrderTypeEnum {
    APPLY("申购"),
    REDEEM("赎回"),
    ;

    /**  */
    private String msg;

    OrderTypeEnum(String msg) {
        this.msg = msg;
    }
}
