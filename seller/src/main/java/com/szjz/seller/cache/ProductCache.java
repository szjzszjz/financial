package com.szjz.seller.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.szjz.api.ProductRpcService;
import com.szjz.api.model.ProductRpcReq;
import com.szjz.enums.ProductStatusEnum;
import com.szjz.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author:szjz
 * date:2019/6/21
 *
 * 产品缓存
 */
@Component
@Slf4j
public class ProductCache {

    public static final String cache_name = "CACHE_NAME";

    @Resource
    private ProductRpcService productRpcService;
    @Resource
    private HazelcastInstance hazelcastInstance;




    @Cacheable(cacheNames = cache_name)
    public Product readCache(String id){
        log.info("查询单个产品 id={}", id);
        Product product = productRpcService.findOne(id);
        log.info("查询单个产品 product={}", product);
        return product;
    }

    public List<Product> readAllCache() {
        Map map = hazelcastInstance.getMap(cache_name);
        List<Product> productList = new ArrayList<>();
        if (map.size()>0){
            productList.addAll(map.values());
        }else {
            productList = findAll();
        }
        return productList;
    }

    //注：如果在该方法上面添加缓存注解，则该方法在当前类中调用无效
    public List<Product> findAll() {
        ProductRpcReq productRpcReq = new ProductRpcReq();
        List<ProductStatusEnum> statusList = new ArrayList<>();
        statusList.add(ProductStatusEnum.AUDITING);
        productRpcReq.setStatusEnumList(statusList);
        productRpcReq.setPageNum(0);
        productRpcReq.setPageSize(100);
        productRpcReq.setDirection(Sort.Direction.DESC);
        productRpcReq.setOrderBy("rewardRate");
        List<Product> productList = productRpcService.query(productRpcReq);
        log.info("查询结果：数量={},结果集={}", productList.size(), productList);
        return productList;
    }

    /** 将产品放入缓存 */
    @CachePut(cacheNames = cache_name,key = "#product.id")
    public Product putCache(Product product){
        return product;
    }

    /** 移除缓存 */
    @CacheEvict(cacheNames = cache_name)
    public void removeCache(String id){

    }
}
