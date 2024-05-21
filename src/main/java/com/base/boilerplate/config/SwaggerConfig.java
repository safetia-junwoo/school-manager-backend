package com.base.boilerplate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi projectAPI(){
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info().title("Spring Boot Rest API Documentation")
                .description("posco backend API")
                .version("1.0"));
    }
}
