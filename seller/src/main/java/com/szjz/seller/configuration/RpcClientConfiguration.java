package com.szjz.seller.configuration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.szjz.api.ProductRpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * author:szjz
 * date:2019/6/19
 *
 * rpc客户端配置
 */

@Configuration
@ComponentScan(basePackageClasses = {ProductRpcService.class})
@Slf4j
public class RpcClientConfiguration {

    @Bean
    public AutoJsonRpcClientProxyCreator clientProxyCreator(@Value("${rpc.manager.url}") String url){
        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
        try {
            creator.setBaseUrl(new URL(url));  //设置服务端url
        } catch (MalformedURLException e) {
            log.error("创建rpc服务端地址错误");
            e.printStackTrace();
        }
        //api模块的服务接口所在包
        creator.setScanPackage(ProductRpcService.class.getPackage().getName()); //设置扫描包
        return creator;
    }
}
