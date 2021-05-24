package com.commerce.config;

import com.commerce.constants.SwaggerConstants;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@AllArgsConstructor
public class SwaggerConfiguration {

    private final SwaggerConstants swaggerConstants;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).host(swaggerConstants.getHostAddress())
                .pathMapping(swaggerConstants.getHostPath()).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(swaggerConstants.getApiName()).description(swaggerConstants.getApiName())
                .contact(contact()).version(swaggerConstants.getApiVersion()).build();
    }

    private Contact contact() {
        return new Contact(swaggerConstants.getContactName(), swaggerConstants.getContactUrl(),
                swaggerConstants.getContactEmail());
    }

}
