package com.szjz.api.model;

import com.szjz.enums.ProductStatusEnum;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/19
 *
 * 产品相关请求对象
 */

@Data
public class ProductRpcReq {

    private Integer pageNum;
    private Integer pageSize;
    /** 排序 */
    private Sort.Direction direction;
    /** 通过某个字段排序 */
    private String orderBy;

    private List<String> idList;
    private BigDecimal minRewardRate;
    private BigDecimal maxRewardRate;
    private List<ProductStatusEnum> statusEnumList;


}
