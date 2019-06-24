package com.szjz.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * author:szjz
 * date:2019/6/19
 */

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//不加载数据源

@EnableScheduling //开启定时任务
@SpringBootApplication
@EnableCaching//开启缓存注解
@EnableJpaRepositories(basePackages = {"com.szjz.model.base","com.szjz.seller.repository"})
@EntityScan(basePackages = {"com.szjz.model","com.szjz.seller.sign"})
public class SellerApp {

    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class);
    }
}
