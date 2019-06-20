package com.szjz.utils.jsonrpc4j.configuration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcClientProxyCreator;
import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * author:szjz
 * date:2019/6/20
 * 
 * rpc 服务端和客户端配置
 */
@Slf4j
@Configuration
public class JsonRpcConfiguration {
    
    
    /** 服务端 */
    @Bean
    public AutoJsonRpcServiceImplExporter serviceImplExporter(){
        return new AutoJsonRpcServiceImplExporter();
    }

    /** 客户端 */
    @Bean
    @ConditionalOnProperty(value = {"rpc.server.url","rpc.server.scanPackage"})//两者配置有值的时候才导出客户端
    public AutoJsonRpcClientProxyCreator clientProxyCreator(@Value("${rpc.server.url}") String url,
                                                            @Value("${rpc.server.scanPackage}") String scanPackage){
        AutoJsonRpcClientProxyCreator creator = new AutoJsonRpcClientProxyCreator();
        try {
            creator.setBaseUrl(new URL(url));  //设置服务端url
        } catch (MalformedURLException e) {
            log.error("创建rpc服务端地址错误");
            e.printStackTrace();
        }
        //api模块的服务接口所在包
        creator.setScanPackage(scanPackage); //设置扫描包
        return creator;
    }
    
}
