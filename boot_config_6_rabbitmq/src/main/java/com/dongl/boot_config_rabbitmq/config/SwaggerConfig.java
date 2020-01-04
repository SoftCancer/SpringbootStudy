package com.dongl.boot_config_swagger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 通过 @Configuration注解，让Spring来加载该类配置。
 * 再通过 @EnableSwagger2注解来启用Swagger2。
 * @description: 配置Swagger API
 * @author: YaoGuangXun
 * @date: 2020/1/1 16:09
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig  {

    @Value(value = "${spring.swagger2.enabled}")
    private Boolean swaggerEnable;

    @Bean
    public Docket createSwaggerApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                // apiInfo()用来创建该Api的基本信息
                .apiInfo(apiInfo())
                .enable(swaggerEnable)
                // select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现，
                // 本例采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.dongl.boot_config_swagger"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("东朗教育网站API")
                .description("网站开发接口Api")
                .termsOfServiceUrl("192.168.1.107")
                .contact(new Contact("程序员Dongl","192.168.1.107","qq.com"))
                .version("1.0")
                .build();
    }

}
