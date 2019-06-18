package com.szjz.model.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * author:szjz
 * date:2019/6/18
 */
public interface BaseRepository<T extends BaseEntity> extends JpaRepository<T,Object>, JpaSpecificationExecutor<T> {

}
