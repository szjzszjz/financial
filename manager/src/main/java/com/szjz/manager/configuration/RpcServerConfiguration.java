package com.szjz.manager.configuration;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author:szjz
 * date:2019/6/19
 *
 * rpc 服务端相关配置
 */

@Configuration
public class RpcServerConfiguration {

    @Bean
    public AutoJsonRpcServiceImplExporter serviceImplExporter(){
        return new AutoJsonRpcServiceImplExporter();
    }
}
