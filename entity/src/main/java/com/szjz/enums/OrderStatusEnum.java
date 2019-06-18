package com.szjz.enums;

import lombok.Getter;

/**
 * author:szjz
 * date:2019/6/18
 */
@Getter
public enum OrderStatusEnum {

    INIT(0,"初始化"),
    PROCESS(1,"处理中"),
    SUCCESS(2,"成功"),
    FAIL(3,"失败"),
    ;

    private Integer code;
    /** msg */
    private String msg;
    /**  */
    private String name;
    /**  */

    OrderStatusEnum(Integer code,String msg) {
        this.msg = msg;
        this.name = super.name();
        this.code = code;
    }
}
