package me.aekrylov.technaxis_test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 3/25/19 7:51 PM
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .globalResponseMessage(RequestMethod.GET, Collections.singletonList(new ResponseMessage(200, "OK", null, null, null)))
                .select()
                .apis(RequestHandlerSelectors.basePackage("me.aekrylov"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Simple book service")
                .description("TECHNAXIS test app")
                .contact(new Contact("Anton Krylov", "https://github.com/aekrylov", null))
                .build();
    }
}