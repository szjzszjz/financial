package com.szjz.seller.service.Impl;

import com.szjz.api.ProductRpcService;
import com.szjz.enums.OrderStatusEnum;
import com.szjz.enums.OrderTypeEnum;
import com.szjz.model.Order;
import com.szjz.model.Product;
import com.szjz.model.base.BaseServiceImpl;
import com.szjz.seller.repository.OrderRepository;
import com.szjz.seller.service.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;

/**
 * author:szjz
 * date:2019/6/21
 */

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {


    @Resource
    private ProductRpcService productRpcService;
    @Resource
    private OrderRepository orderRepository;

    @Override
    public Order create(Order order) {
        //数据校验
        checkOrder(order);

        //完善订单信息
        Order result = completeOrder(order);
        return result;
    }

    /** 完善订单信息 */
    private Order completeOrder(Order order) {
        order.setOrderType(OrderTypeEnum.APPLY);
        order.setOrderStatus(OrderStatusEnum.SUCCESS);
//        order.setUpdateTime(new Date());
        Order result = orderRepository.saveAndFlush(order);
        return result;
    }

    /** 数据校验 */
    private void checkOrder(Order order) {
        Assert.notNull(order.getOuterOrderId(),"需要外部订单编号");
        Assert.notNull(order.getChanId(),"需要渠道编号");
        Assert.notNull(order.getChanUserId(),"需要渠道用户编号");
        Assert.notNull(order.getProductId(),"需要产品编号");
        Assert.notNull(order.getAmount(),"需要购买金额");
//        Assert.notNull(order.getCreateTime(),"需要订单时间");

        Product product = productRpcService.findOne(order.getProductId());
        Assert.notNull(product,"产品不存在");
    }
}
