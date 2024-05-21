package com.base.boilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing
@ConfigurationPropertiesScan("com.base.boilerplate")
public class BoilerPlateApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoilerPlateApplication.class, args);
    }

    @RestController
    public class WelcomeController {
        @GetMapping("/")
        public String welcome() {
            return "welcome-ec2";
        }
    }

}
