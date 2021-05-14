package com.commerce.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class SwaggerConstants {

    @Value("${swagger.host.address}")
    private String hostAddress;

    @Value("${swagger.host.path}")
    private String hostPath;

    @Value("${swagger.api.name}")
    private String apiName;

    @Value("${swagger.api.version}")
    private String apiVersion;

    @Value("${swagger.contact.name}")
    private String contactName;

    @Value("${swagger.contact.url}")
    private String contactUrl;

    @Value("${swagger.contact.email}")
    private String contactEmail;

}
