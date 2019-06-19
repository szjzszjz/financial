package com.szjz.model.base;

import com.szjz.result.Result;
import com.szjz.result.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.Assert;

import java.util.List;

/**
 * author:szjz
 * date:2019/6/18
 */

@Slf4j
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    @Autowired
    private BaseRepository baseRepository;

    @Override
    public T findOne(Object id) {
        Assert.notNull(id, "商品编号为空");
        log.debug("查询单个产品 id={}", id);
        T t = (T) baseRepository.findById(id).orElse(null);
        log.debug("查询单个产品 结果={}", t);
        return t;
    }

    /**
     * 分页查询数据 已存在的所有数据
     */
    @Override
    public Page<T> findAll(Integer pageNumber, Integer pageSize) {
        PageRequest request = PageRequest.of(pageNumber, pageSize);
        Page<T> tPage = baseRepository.findAll(request);
        return tPage;
    }

    /**
     * 根据ids批量删除
     */
    @Override
    public void deleteByIds(List<Object> idList) {
        if (idList != null && idList.size() > 0) {
            log.debug("批量删除：ids={}", idList);
            for (Object id : idList) {
                if (!StringUtils.isEmpty(id.toString())) {
                    T t1 = (T) baseRepository.findById(id).orElse(null);
                    baseRepository.delete(t1);
                } else {
                    Assert.notNull(id, "存在id为空或者空串");
                }
            }
        } else {
            Assert.notNull(idList, "idList为空");
        }
    }

    /**
     * 计算总量
     */
    @Override
    public Long count(Integer integer) {
        long count = baseRepository.count();
        log.info("总量：{}",count);
        return count;
    }

    /**
     * 检测对象的存在
     */
    @Override
    public Object findObject(Object obj, Boolean hope) {
        //希望存在
        if (hope) {
            //不存在抛异常
            if (obj == null) {
                Assert.notNull(obj, ResultEnum.RECORD_DOES_NOT_EXIST.getMsg());
            }
            //存在 返回对象
            else {
                return obj;
            }
        }
        //不希望存在
        else {
            //存在抛异常
            if (obj != null) {
                Assert.notNull(obj, ResultEnum.RECORD_HAVE_EXIST.getMsg());
            }
        }
        return null;
    }
}
