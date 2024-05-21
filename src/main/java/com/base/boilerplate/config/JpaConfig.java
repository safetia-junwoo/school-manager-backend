package com.base.boilerplate.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.base.boilerplate")
@EntityScan(basePackages = "com.base.boilerplate")
@EnableJpaRepositories(basePackages = "com.base.boilerplate")
public class JpaConfig {
}
