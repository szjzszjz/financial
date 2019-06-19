package com.szjz.manager;

import com.szjz.swagger.configuration.SwaggerConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger.configuration.SwaggerCommonConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.boot.SpringApplication.run;

/**
 * author:szjz
 * date:2019/6/18
 *
 * 管理端启动类
 */

@SpringBootApplication
//在swagger模块中配置spring.factories 可省去以下两个注解
//@EnableSwagger2
//@Import(SwaggerConfig.class) //导入swagger配置类
//因为实体类和repository没有在一个模块中 所有需要添加实体扫描注解 否则报错：Not a managed type
@EntityScan(basePackages = {"com.szjz.model"})
public class ManagerApp {
    public static void main(String[] args) {
        run(ManagerApp.class);
    }
}
