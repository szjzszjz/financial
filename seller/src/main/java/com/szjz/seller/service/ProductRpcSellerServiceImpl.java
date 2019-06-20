package com.szjz.seller.service;

import com.szjz.api.ProductRpcService;
import com.szjz.api.model.ProductRpcReq;
import com.szjz.enums.ProductStatusEnum;
import com.szjz.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/19
 */

@Component
@Slf4j
public class ProductRpcSellerServiceImpl {

    @Autowired
    private ProductRpcService productRpcService ;

    public List<Product> findAll(){
        ProductRpcReq productRpcReq = new ProductRpcReq();
        List<ProductStatusEnum> statusList = new ArrayList<>();
        statusList.add(ProductStatusEnum.AUDITING);
        productRpcReq.setStatusEnumList(statusList);
        productRpcReq.setPageNum(0);
        productRpcReq.setPageSize(100);
        productRpcReq.setDirection(Sort.Direction.DESC);
        productRpcReq.setOrderBy("rewardRate");
        List<Product> productList = productRpcService.query(productRpcReq);
        log.info("查询结果：数量={},结果集={}",productList.size(),productList);
        return productList;
    }

    @PostConstruct
    public void test(){
        findAll();
    }

}
