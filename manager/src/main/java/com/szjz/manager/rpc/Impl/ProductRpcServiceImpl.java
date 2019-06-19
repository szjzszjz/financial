package com.szjz.manager.rpc.Impl;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import com.szjz.api.ProductRpcService;
import com.szjz.api.model.ProductRpcReq;
import com.szjz.manager.service.ProductService;
import com.szjz.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:szjz
 * date:2019/6/19
 * rpc实现类
 */

@Slf4j
@AutoJsonRpcServiceImpl
@Service
public class ProductRpcServiceImpl implements ProductRpcService {

    @Autowired
    private ProductService productService;

    @Override
    public List<Product> query(ProductRpcReq req) {
        log.info("动态查询多个产品：req={}",req);
        Page<Product> productPage = productService.queryList(req.getPageNum(), req.getPageSize(), req.getIdList(), req.getMinRewardRate(), req.getMaxRewardRate(), req.getStatusEnumList());
        log.info("动态查询多个产品：res={}",productPage.getTotalElements());
        return productPage.getContent();
    }

    @Override
    public Product findOne(String id) {
        log.info("查询单个产品：id={}",id);
        Product one = productService.findOne(id);
        log.info("查询单个产品：res={}",one);
        return one;
    }
}
