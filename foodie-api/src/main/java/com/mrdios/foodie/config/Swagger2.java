package com.mrdios.foodie.config;

import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 配置
 * Swagger2 接口文档前端访问地址:
 * 传统：/swagger-ui.html
 * 优化：/doc.html
 *
 * @author CodePorter
 * @date 2020-06-20
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    /**
     * Swagger2 配置
     */
    public Docket apiDocConfig() {
        return new Docket(DocumentationType.SWAGGER_2)      // 指定api类型
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mrdios.foodie.api.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API接口文档")
                .contact(new Contact("CodePorter", "http://www.mrdios.com", "balderdasher@msn.com"))
                .description("前端WEB接口文档")
                .version("1.0")
                .termsOfServiceUrl("http://www.mrdios.com")
                .build();
    }
}
