package com.zd.demodeploycicd.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("User Management API")
                        .description("API for managing users in the demo CI/CD project")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Demo CI/CD")
                                .email("demo@example.com")));
    }
}