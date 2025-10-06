package com.example.realestate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI realEstateOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Real Estate Management API")
                        .description("API for managing property rentals")
                        .version("1.0"));
    }
}