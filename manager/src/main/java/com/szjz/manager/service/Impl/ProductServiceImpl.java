package com.szjz.manager.service.Impl;

import com.szjz.enums.ProductStatusEnum;
import com.szjz.manager.repository.ProductRepository;
import com.szjz.manager.service.ProductService;
import com.szjz.model.Product;
import com.szjz.model.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/18
 */

@Service
@Slf4j
public class ProductServiceImpl extends BaseServiceImpl<Product> implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    public Product create(Product product) {
        log.debug("创建产品 {}", product);
        //数据校验 根据业务需求编写数据校验内容
        checkProduct(product);
        //设置默认值
        setDefault(product);
        Product result = productRepository.saveAndFlush(product);
        log.debug("产品创建完成 {}", result);
        return result;
    }


    private void setDefault(Product product) {
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        product.setStepAmount(BigDecimal.ZERO);
        product.setLockTerm(0);
        product.setStatus(ProductStatusEnum.AUDITING);
    }

    /**
     * 校验投资步长 为整数
     * 校验收益率 0~30
     */
    private void checkProduct(Product product) {
        Assert.notNull(product.getId(), "编号不可为空");
        Assert.isTrue(BigDecimal.ZERO.compareTo(product.getRewardRate()) < 0 && BigDecimal.valueOf(30).compareTo(product.getRewardRate()) >= 0, "收益率范围错误");
        Assert.isTrue(BigDecimal.valueOf(product.getStepAmount().longValue()).compareTo(product.getStepAmount()) == 0, "投资步长为整数");

    }

    /**
     * 多条件动态查询
     */
    public Page<Product> queryList(Integer pageNum,
                                   Integer pageSize,
                                   List<String> idList,
                                   BigDecimal minRewardRate,
                                   BigDecimal maxRewardRate,
                                   List<ProductStatusEnum> statusEnumList) {
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);


        Specification specification = (Specification<Product>) (root, query, cb) -> {

            List<Predicate> predicateList = new ArrayList<>();
            if (idList!=null && idList.size()>0){
                predicateList.add(root.get("id").in(idList));
            }
            if (minRewardRate!=null && BigDecimal.ZERO.compareTo(minRewardRate)<0){
                predicateList.add(cb.ge(root.get("rewardRate"),minRewardRate));
            }
            if (maxRewardRate!=null && BigDecimal.valueOf(30).compareTo(maxRewardRate)>=0){
                predicateList.add(cb.le(root.get("rewardRate"),maxRewardRate));
            }
            if (statusEnumList!= null && statusEnumList.size()>0){
                predicateList.add(root.get("status").in(statusEnumList));
            }
            query.where(predicateList.toArray(new Predicate[0]));
            return null;
        };
        Page page = productRepository.findAll(specification, pageRequest);
        log.debug("动态查询结果={}",page);
        return page;

    }

}
