package com.szjz.seller.service;

import com.szjz.model.Order;
import com.szjz.model.base.BaseService;

/**
 * author:szjz
 * date:2019/6/21
 */
public interface OrderService extends BaseService<Order> {

    /** 创建订单 */
    Order create(Order order);
}
