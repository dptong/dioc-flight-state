/**
 * @Title: Swagger2Configuration.java
 * @Package com.flywin.sys.config
 * @Description: TODO
 * @author: 陈英杰
 * @date 2019年6月20日   O
 */
package com.flywin.fsm.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    /**
     * @return
     * @return Docket
     * @Title createRestApi
     * @Description
     * @author 刘二奇
     * @date: 2019年8月19日
     */
    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<springfox.documentation.service.Parameter> pars = new ArrayList<>();
        tokenPar.name("Authorization").description("令牌").modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        pars.add((springfox.documentation.service.Parameter) tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.flywin"))
                .paths(PathSelectors.any()).build().globalOperationParameters(pars);

    }

    /**
     * @return
     * @return ApiInfo
     * @Title apiInfo
     * @Description
     * @author 刘二奇
     * @date: 2019年8月19日
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("飞机参数配置api文档").description("飞机参数配置api文档").version("1.0").build();
    }
}
