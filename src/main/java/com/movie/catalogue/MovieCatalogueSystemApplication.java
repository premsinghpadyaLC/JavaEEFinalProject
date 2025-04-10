/*
 * Author: Premsingh Padya
 * Student ID: C0924501
 * Project Name: Movie Catalogue System
 *
 * Description: This is the entry point for the Movie Catalogue System Spring Boot application.
 * It starts the Spring Boot application by invoking the `run` method on the `SpringApplication` class.
 * This is the main class that launches the application context and initializes the Spring Boot environment.
 * 
 * The `@SpringBootApplication` annotation is a convenience annotation that includes:
 * - @Configuration: Specifies that this class contains Spring configuration.
 * - @EnableAutoConfiguration: Tells Spring Boot to automatically configure the application based on the dependencies.
 * - @ComponentScan: Enables component scanning, which means Spring will automatically detect and register beans.
 */
package com.movie.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieCatalogueSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieCatalogueSystemApplication.class, args);
    }
}
