package com.szjz.model;

import com.szjz.enums.ProductStatusEnum;
import com.szjz.utils.KeyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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
    private String id = KeyUtil.genUniqueKey();

    /** 产品名称 */
    @ApiModelProperty(value = "产品名称") //swagger文档属性标注
    private String name;

    /** 状态 */
    @ApiModelProperty(value = "状态",dataType = "com.szjz.enums.ProductStatusEnum")
    private ProductStatusEnum status;
    
    /** 起投金额 */
    @ApiModelProperty(value = "起投金额")
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
