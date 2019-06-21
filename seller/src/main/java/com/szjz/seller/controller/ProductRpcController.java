package com.szjz.seller.controller;

import com.szjz.api.ProductRpcService;
import com.szjz.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:szjz
 * date:2019/6/21
 */

@RestController
@Slf4j
@RequestMapping("/rpcProduct")
public class ProductRpcController {

    @Autowired(required = false)
    private ProductRpcService productRpcService;

    @GetMapping("/{id}")
    @Cacheable(cacheNames = "product")
    public Product findOne(@PathVariable String id){
        Product product = productRpcService.findOne(id);
        return product;
    }
}
