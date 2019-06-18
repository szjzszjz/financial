package com.szjz.enums;

import com.szjz.model.Product;
import lombok.Getter;

/**
 * author:szjz
 * date:2019/6/18
 *
 * 产品状态枚举
 */

@Getter
public enum ProductStatusEnum {
    AUDITING("审核中"),
    IN_SELL("销售中"),
    LOCKED("暂停销售"),
    FINISHED("已结束"),
    ;


    /** msg */
    private String msg;

    ProductStatusEnum(String msg) {
        this.msg = msg;
    }

    public static ProductStatusEnum getEnum(String name){
        for (ProductStatusEnum ele:ProductStatusEnum.values()){
            if (ele.name().equals(name)) {
                return ele;
            }
        }
        return null;
    }
}
