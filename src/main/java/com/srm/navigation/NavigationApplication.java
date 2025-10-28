package com.srm.navigation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories(basePackages = "com.srm.navigation.entity.repository")
@SpringBootApplication
public class NavigationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NavigationApplication.class, args);
    }
}