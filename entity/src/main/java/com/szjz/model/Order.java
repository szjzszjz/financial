package com.szjz.model;

import com.szjz.enums.OrderStatusEnum;
import com.szjz.enums.OrderTypeEnum;
import com.szjz.model.base.BaseEntity;
import com.szjz.utils.KeyUtil;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * author:szjz
 * date:2019/6/18
 */
@Data
@Entity(name = "order_t")
public class Order extends BaseEntity {

    private static final long serialVersionUID = -2633760291469112683L;

    /** 渠道编号 */
    private String chanId;

    /** 产品编号 */
    private String productId;

    /** 渠道用户编号 */
    private String chanUserId;

    /** 订单类型 */
    private OrderTypeEnum orderType;

    /** 订单状态 */
    private OrderStatusEnum orderStatus;

    /** 外部订单编号 */
    private String outerOrderId;

    /** 金额 */
    private BigDecimal amount;



}
