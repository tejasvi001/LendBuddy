package com.example.LendBuddy.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LendBuddy API")
                        .description("API documentation for LendBuddy loan & collection management system")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("LendBuddy Support")
                                .email("support@lendbuddy.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}

