package com.base.boilerplate.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({GlobalSettings.class})
public class PropertyConfig {
}
