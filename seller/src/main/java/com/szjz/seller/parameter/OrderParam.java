package com.szjz.seller.parameter;


import com.szjz.seller.sign.SignText;
import lombok.Data;

import java.math.BigDecimal;

/**
 * author:szjz
 * date:2019/6/22
 */


@Data
public class OrderParam implements SignText {
    /** 渠道编号 */
    private String chanId;

    /** 产品编号 */
    private String productId;

    /** 渠道用户编号 */
    private String chanUserId;

    /** 外部订单编号 */
    private String outerOrderId;

    /** 金额 */
    private BigDecimal amount;

    public OrderParam(String chanId, String productId, String chanUserId, String outerOrderId, BigDecimal amount) {
        this.chanId = chanId;
        this.productId = productId;
        this.chanUserId = chanUserId;
        this.outerOrderId = outerOrderId;
        this.amount = amount;
    }
}
