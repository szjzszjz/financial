package com.szjz.swagger.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * author:szjz
 * date:2019/6/19
 */
@Component
@ConfigurationProperties(prefix = "swagger")
@Data
public class SwaggerProperty {

    /** 文档组名 */
    private String groupName = "default";
    
    /** Controller所在包名 */
    private String basePackage;

    /** 指定url路径 */
    private String path;

    /** 文档标题 */
    private String title = "Restful Api";

    /** 文档描述 */
    private String desc = "后台管理API";


}
