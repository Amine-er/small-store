package com.errabi.microservice1.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .info(new Info()
                        .title("Microservice1 API")
                        .version("1.0")
                        .description("API documentation for Microservice1")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")));
    }
}
