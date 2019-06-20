package com.szjz.manager.service;

import com.szjz.enums.ProductStatusEnum;
import com.szjz.model.Product;
import com.szjz.model.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/18
 */
public interface ProductService extends BaseService<Product> {

    Product create(Product product);

    Page<Product> queryList(Integer pageNum,
                            Integer pageSize,
                            Sort.Direction direction,
                            String orderBy,
                            List<String> idList,
                            BigDecimal minRewardRate,
                            BigDecimal maxRewardRate,
                            List<ProductStatusEnum> statusEnumList);

}
