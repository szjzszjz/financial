package com.szjz.manager.controller;

import com.szjz.enums.ProductStatusEnum;
import com.szjz.manager.service.ProductService;
import com.szjz.model.Product;
import com.szjz.model.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/18
 */

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/create")
    public Result create(Product product) {
        Product product1 = productService.create(product);
        return Result.success(product1);
    }

    @GetMapping(value = "/{id}")
    public Result create(@PathVariable String id) {
        log.info("查询单个产品 id={}", id);
        Product product1 = productService.findOne(id);
        return Result.success(product1);
    }

    @GetMapping(value = "/queryList")
    public Result queryList(@RequestParam(defaultValue = "0") Integer pageNum,
                            @RequestParam(defaultValue = "10") Integer pageSize,
                            String idList,
                            BigDecimal minRewardRate,
                            BigDecimal maxRewardRate,
                            String statusEnumList) {
        log.info("动态分页查询 pageNum={} ,pageSize={} ,idList={},minRewardRate={},maxRewardRate={},statusEnumList={}",
                pageNum, pageSize, idList, minRewardRate, maxRewardRate, statusEnumList);
        List<String> ids = null;
        List<ProductStatusEnum> statusEnums = null;
        if (!StringUtils.isEmpty(idList)) {
            ids = Arrays.asList(idList.split(","));
        }
        if (!StringUtils.isEmpty(statusEnumList)) {
            String[] status = statusEnumList.split(",");
            for (String name:status){
                ProductStatusEnum statusEnum = ProductStatusEnum.getEnum(name);
                statusEnums.add(statusEnum);
            }
        }
        Page<Product> productPage = productService.queryList(pageNum, pageSize, ids, minRewardRate, maxRewardRate, statusEnums);
        return Result.success(productPage);
    }

}
