package com.example.Ejercicio456.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * Configuración Swagger para la generación de documentación de la API REST
 *  localhost:8080/swagger-ui/  (Puerto 8081 en este caso)
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiDetails())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Curso de Spring - Ejercicio 7-8-9",
                "Open Bootcamp",
                "1.0",
                "https://open-bootcamp.com",
                new Contact("Alejandro", "https://github.com/no7sag", "email@deprueba.com"),
                "Creative Commons",
                "https://creativecommons.org",
                Collections.emptyList());
    }
}
