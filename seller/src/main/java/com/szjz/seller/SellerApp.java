package com.szjz.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * author:szjz
 * date:2019/6/19
 */

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//不加载数据源

@SpringBootApplication
@EnableCaching//开启缓存注解
@EnableSwagger2
@EntityScan(basePackages = "com.szjz.model")
public class SellerApp {

    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class);
    }
}
