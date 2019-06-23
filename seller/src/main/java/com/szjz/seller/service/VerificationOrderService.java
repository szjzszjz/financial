package com.szjz.seller.service;

import com.szjz.model.VerificationOrder;
import com.szjz.model.base.BaseService;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/23
 */
public interface VerificationOrderService extends BaseService<VerificationOrder> {

    /** 查询验证订单 */
    List<String> queryVerificationOrders(String chanId, Date start, Date end);

    File makeVerificationFile(String chanId, Date day);

}
