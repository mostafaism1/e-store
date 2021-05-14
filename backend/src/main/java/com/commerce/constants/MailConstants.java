package com.commerce.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class MailConstants {

    @Value("${spring.mail.host_address}")
    private String hostAddress;

}