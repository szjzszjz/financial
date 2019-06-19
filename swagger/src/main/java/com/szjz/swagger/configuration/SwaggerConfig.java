package com.szjz.swagger.configuration;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author szjz
 * @date 2019/5/8 11:16
 * 配置swagger
 */

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "com.szjz.swagger.configuration")
public class SwaggerConfig {
    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

    @Autowired
    private SwaggerProperty swaggerProperty;

    /** 可以存在多个docket 一个bean 代表一组 */
    @Bean
    public Docket createRestApi() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)//
                .groupName(swaggerProperty.getGroupName())//组名
                .apiInfo(apiInfo());//
//                .select()//
//                .apis(RequestHandlerSelectors.basePackage(ProductController.class.getPackage().getName()))//指定包名
//                .paths(PathSelectors.any())//匹配url路径any():任何路径  ant("v1/api/..."):指定路径
//                .build()//


        ApiSelectorBuilder builder = docket.select();
        if (!StringUtils.isEmpty(swaggerProperty.getBasePackage())){
            builder.apis(RequestHandlerSelectors.basePackage(swaggerProperty.getBasePackage()));
        }
        if (!StringUtils.isEmpty(swaggerProperty.getPath())){
            builder.paths(PathSelectors.ant(swaggerProperty.getPath()));
        }
        return docket;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()//
                .title(swaggerProperty.getTitle())                                      //标题
                .description(swaggerProperty.getDesc())                               //描述
                .contact(new Contact("石中君子", "", ""))  //联系人
                .version("1.0")                                          //版本号
                .build();
    }

}
