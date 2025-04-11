package com.example.weather_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WeatherBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherBackendApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        // Consider adding configurations like timeouts, error handlers later
        return new RestTemplate();
    }
}