package com.szjz.seller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * author:szjz
 * date:2019/6/19
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//不加载数据源
public class SellerApp {

    public static void main(String[] args) {
        SpringApplication.run(SellerApp.class);
    }
}
