package com.books.librarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.books.librarymanagementsystem.config.CustomAuditorAware;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;

/**
 * 
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@OpenAPIDefinition(
    info = @Info(title = "Library Management System APIs", version = "1.0",
        description = "API documentation for Library Management System."),
    security = {@SecurityRequirement(name = "bearerToken")})
@SecuritySchemes({@SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP,
    scheme = "bearer", bearerFormat = "JWT")})
public class LibraryManagementSystemApplication {

  public static void main(String[] args) {

    SpringApplication.run(LibraryManagementSystemApplication.class, args);
  }

  @Bean
  public AuditorAware<String> auditorAware() {

    return new CustomAuditorAware();
  }
}
