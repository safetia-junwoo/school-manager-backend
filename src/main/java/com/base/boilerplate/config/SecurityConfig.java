package com.base.boilerplate.config;

import com.base.boilerplate.config.jwt.JwtSecurityFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String USER = "USER";
    private static final String ADMIN = "ADMIN";
    private static final String CHARGER = "CHARGER";
    private static String AUTH = "/auth/**";
    private static String BATCH = "/batch/**";
    private static String API_V1_VERSION = "/api/v1";
    private static String PUBLIC_API = "/public/**";
    private static String PRIVATE_API = "/private/**";

    private final JwtSecurityFilter jwtSecurityFilter;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers(API_V1_VERSION+PRIVATE_API).hasAnyAuthority(ADMIN, CHARGER)
                .antMatchers(API_V1_VERSION+PUBLIC_API).hasAnyAuthority(ADMIN, CHARGER, USER)
                .antMatchers(API_V1_VERSION+AUTH).permitAll()
                .antMatchers(API_V1_VERSION+BATCH).permitAll()
                .and()
                .apply(jwtSecurityFilter);
        return http.build();
    }
}
