package com.szjz.manager.controller;

import com.szjz.enums.ProductStatusEnum;
import com.szjz.manager.service.ProductService;
import com.szjz.model.Product;
import com.szjz.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/18
 */

@RestController
@RequestMapping("api/v1/product")
@Slf4j
//@Api(tags = "product",description = "产品相关") //swagger controller标签和描述
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping(value = "")
    @ApiOperation(value = "添加产品",notes = "",response = Result.class)
    public Result create(@RequestParam String name,
                         @RequestParam ProductStatusEnum status,
                         @RequestParam BigDecimal thresholdAmount,
                         @RequestParam BigDecimal stepAmount,
                         @RequestParam Integer lockTerm,
                         @RequestParam BigDecimal rewardRate,
                         @RequestParam(required = false) String createUser,
                         @RequestParam(required = false) String updateUser,
                         @RequestParam(required = false) String remark
                         ) {
        Product product = new Product(name, status, thresholdAmount, stepAmount, lockTerm, rewardRate, createUser, updateUser);
        product.setRemark(remark);
        log.info("添加产品：product={}",product);
        Product product1 = productService.create(product);
        return Result.success(product1);
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询单个产品",notes = "",response = Result.class)
    public Result create(@PathVariable String id) {
        log.info("查询单个产品 id={}", id);
        Product product1 = productService.findOne(id);
        return Result.success(product1);
    }

    @GetMapping(value = "/queryList")
    @ApiOperation(value = "动态分页查询",notes = "",response = Result.class)
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
        //按照收益率进行降序排列
        Page<Product> productPage = productService.queryList(pageNum, pageSize, Sort.Direction.DESC,"rewardRate", ids, minRewardRate, maxRewardRate, statusEnums);
        return Result.success(productPage);
    }

}
