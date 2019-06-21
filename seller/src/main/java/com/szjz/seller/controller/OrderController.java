package com.szjz.seller.controller;

import com.szjz.model.Order;
import com.szjz.result.Result;
import com.szjz.seller.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:szjz
 * date:2019/6/21
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    protected OrderService orderService;

    @PostMapping("")
    @ApiOperation(value = "",notes = "",response = Result.class)
    public Result apply(Order order){
        log.info("产品申购：order={}",order);
        Order result = orderService.create(order);
        log.info("申购结果：order={}",order);
        return Result.success(result);
    }
}
