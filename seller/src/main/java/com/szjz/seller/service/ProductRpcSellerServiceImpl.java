package com.szjz.seller.service;

import com.szjz.api.ProductRpcService;
import com.szjz.api.model.ProductRpcReq;
import com.szjz.enums.ProductStatusEnum;
import com.szjz.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/19
 */

@Service
@Slf4j
public class ProductRpcSellerServiceImpl {

    @Autowired
    private ProductRpcService productRpcService;

    public List<Product> findAll(){
        ProductRpcReq productRpcReq = new ProductRpcReq();
        List<ProductStatusEnum> statusList = new ArrayList<>();
        statusList.add(ProductStatusEnum.AUDITING);
        productRpcReq.setStatusEnumList(statusList);
        productRpcReq.setPageNum(0);
        productRpcReq.setPageSize(100);
//        PageRequest.of(0,100, Sort.Direction.DESC,"rewardRate");
        List<Product> productList = productRpcService.query(productRpcReq);
        log.info("查询结果：{}",productList);
        return productList;
    }

    @PostConstruct
    public void test(){
        findAll();
    }

}
