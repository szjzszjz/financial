package com.szjz.seller.service.Impl;

import com.szjz.api.ProductRpcService;
import com.szjz.api.events.ProductStatusEvent;
import com.szjz.api.model.ProductRpcReq;
import com.szjz.enums.ProductStatusEnum;
import com.szjz.model.Product;
import com.szjz.seller.cache.ProductCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.domain.Sort;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/19
 */

@Component
@Slf4j
public class ProductRpcSellerServiceImpl implements ApplicationListener<ContextRefreshedEvent> {

    public static final String MQ_DESTINATION = "Consumer.cache.VirtualTopic.PRODUCT_MQ";

    @Resource
    private ProductRpcService productRpcService;
    @Resource
    private ProductCache productCache;

    public List<Product> findAll() {
        List<Product> productList = productCache.readAllCache();
        return productList;
    }


    public Product findOne(String id) {
        log.info("查询单个产品 id={}", id);
        Product product = productRpcService.findOne(id);
        log.info("查询单个产品 product={}", product);
        return product;
    }

//    @PostConstruct
    public void test() {
//        findAll();
        findOne("aaa");
    }

    //spring容器初始化完成之后会触发该事件
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        List<Product> productList = findAll();
        productList.forEach(product -> {
            productCache.putCache(product);
        });
    }


    @JmsListener(destination = MQ_DESTINATION)
    public void changeStatus(ProductStatusEvent event){

        log.info("receive event ={}",event);
        productCache.removeCache(event.getId());
        //只缓存在售状态的产品
        if (ProductStatusEnum.IN_SELL.equals(event.getStatus())){
            productCache.readCache(event.getId());
        }
    }
}
