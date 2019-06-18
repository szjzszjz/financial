package com.szjz.model;

import com.szjz.enums.ProductStatusEnum;
import lombok.Data;
import com.szjz.model.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * author:szjz
 * date:2019/6/17
 */

@Data
@Entity
public class Product extends BaseEntity {

    private static final long serialVersionUID = 2037980036447645466L;

    @Id
    private String id;

    /** 产品名称 */
    private String name;

    /** 状态 */
    private ProductStatusEnum status;
    
    /** 起投金额 */
    private BigDecimal thresholdAmount;
    
    /** 起投步长 */
    private BigDecimal stepAmount;
    
    /** 锁定期 */
    private Integer lockTerm;

    /** 收益率 */
    private BigDecimal rewardRate;

    /** 创建者 */
    private String createUser;

    /** 更新者 */
    private String updateUser;

}
