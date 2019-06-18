package com.szjz.model.base;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * author:szjz
 * date:2019/6/18
 */
public interface BaseService<T extends BaseEntity> {

    /**
     * 分页查询数据 已存在的所有数据
     */
    Page<T> findAll(Integer pageNumber, Integer pageSize);

    /**
     * 计算总量
     */
    Long count(Integer integer);

    /**
     * 检测对象的存在
     */
    Object findObject(Object obj, Boolean hope);

    /**
     * 查询单个产品
     */
    T findOne(Object id);

    /**
     * 根据ids批量删除
     */
    void deleteByIds(List<Object> idList);


}
