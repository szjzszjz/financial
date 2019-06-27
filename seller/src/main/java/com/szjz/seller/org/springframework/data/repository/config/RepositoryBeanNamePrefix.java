package com.szjz.seller.org.springframework.data.repository.config;

import java.lang.annotation.*;

/**
 * author:szjz
 * date:2019/6/27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RepositoryBeanNamePrefix {

    String value();
}
