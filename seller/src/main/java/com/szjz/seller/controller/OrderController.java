package com.szjz.seller.controller;

import com.szjz.enums.OrderStatusEnum;
import com.szjz.enums.OrderTypeEnum;
import com.szjz.model.Order;
import com.szjz.result.Result;
import com.szjz.seller.parameter.OrderParam;
import com.szjz.seller.service.OrderService;
import com.szjz.seller.service.VerificationOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.util.calendar.BaseCalendar;

import javax.annotation.Resource;
import javax.jws.WebResult;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * author:szjz
 * date:2019/6/21
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {


    @Resource
    protected OrderService orderService;
    @Resource
    private VerificationOrderService verificationOrderService;

    @PostMapping("")
    @ApiOperation(value = "产品申购/创建订单", notes = "", response = Result.class)
    public Result apply(
            @RequestHeader String authId,
            @RequestHeader(required = false) String sign,
            OrderParam orderParam
//            @RequestParam String chanId,
//            @RequestParam String productId,
//            @RequestParam String chanUserId,
//            @RequestParam String outerOrderId,
//            @RequestParam BigDecimal amount
    ) {
//        OrderParam orderParam = new OrderParam(
//                chanId,
//                productId,
//                chanUserId,
//                outerOrderId,
//                amount);
        Order order = new Order();
        BeanUtils.copyProperties(orderParam, order);
        log.info("产品申购：order={}", order);
        Order result = orderService.create(order);
        log.info("申购结果：order={}", order);
        return Result.success(result);
    }

    /** 生成对账文件 */
    @PostMapping("/makeVerificationFile")
    @ApiOperation(value = "生成对账文件", notes = "", response = Result.class)
    public Result makeVerificationFile(@RequestParam String chanId,
                                       @RequestParam(required = false) Date day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date day1 = format.parse("2019-6-23");
        File file = verificationOrderService.makeVerificationFile(chanId, day1);
        return Result.success(file);
    }

    /** 保存验证订单 */
    @PostMapping("/saveVerificationOrder")
    @ApiOperation(value = "保存验证订单", notes = "", response = Result.class)
    public Result saveVerificationOrder(@RequestParam String chanId,
                                       @RequestParam(required = false) Date day) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date day1 = format.parse("2019-6-23");
         verificationOrderService.saveVerificationOrder(chanId, day1);
        return Result.success();
    }
}
