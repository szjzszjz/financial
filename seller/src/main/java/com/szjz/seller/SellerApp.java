package com.szjz.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * author:szjz
 * date:2019/6/19
 */

@SpringBootApplication
//        (exclude = {
//                DataSourceAutoConfiguration.class,
//                HibernateJpaAutoConfiguration.class,
//                DataSourceTransactionManagerAutoConfiguration.class
//        })//不加载数据源
//@EnableTransactionManagement

@EnableScheduling //开启定时任务
@EnableCaching//开启缓存注解
@EnableJpaRepositories(basePackages = {"com.szjz.model.base", "com.szjz.seller.repository", "com.szjz.seller.repositoryBackup"})
@EntityScan(basePackages = {"com.szjz.model", "com.szjz.seller.sign"})
public class SellerApp {

    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class);
    }
}
