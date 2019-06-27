package com.szjz.model;
import com.szjz.enums.OrderTypeEnum;
import com.szjz.model.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * author:szjz
 * date:2019/6/23
 *
 * 订单验证实体
 */

@Data
@Entity
@Table(name = "verification_order")
public class VerificationOrder extends BaseEntity implements Serializable {

    /** 订单编号 */
    private String orderId;

    /** 渠道编号 */
    private String chanId;

    /** 产品编号 */
    private String productId;

    /** 渠道用户编号 */
    private String chanUserId;

    /** 订单类型 */
    private OrderTypeEnum orderType;

    /** 外部订单编号 */
    private String outerOrderId;

    /** 金额 */
    private BigDecimal amount;

}
