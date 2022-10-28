package com.glriverside.menus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

/**
 * @Author 快乐小柴
 * @Date 2022/10/24 9:18
 * @Version 1.0
 */
@EnableOpenApi
@Configuration
//@Profile({"dev", "test"})
public class SwaggerConfig {
    @Bean
    public Docket docket(Environment environment) {
        //设置需要配置swagger的项目环境
        Profiles profiles = Profiles.of("dev","test");
        //获取项目环境
       boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                //设置分组的名字，如果其他人也有分组，其他人就new一个docket
                .groupName("陈水明的api文档")
                //是否开启swagger
                .enable(flag)
                .select()
                //扫描包的的位置
                .apis(RequestHandlerSelectors.basePackage("com.glriverside.menus.controller"))
                //需要过滤扫描包的位置
                //.paths()
                .build();
    }
    private ApiInfo apiInfo(){
        Contact contact = new Contact("陈水明", "http://localhost:8088/menus", "439752552@qq.com");
        return new ApiInfo(
                "陈水明的第一个Api文档",
                "Api Documentation",
                "1.0",
                "http://localhost:8088/menus",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
