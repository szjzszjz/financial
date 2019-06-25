package com.szjz.api;

import com.googlecode.jsonrpc4j.JsonRpcService;
import com.szjz.api.model.ProductRpcReq;
import com.szjz.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * author:szjz
 * date:2019/6/19
 *
 * 产品相关的rpc服务接口
 */

@JsonRpcService()
public interface ProductRpcService {

    /** 动态查询多个产品 */
    List<Product> query(ProductRpcReq productRpcReq);

    /** 查询单个产品 */
    Product findOne(String id);


}
