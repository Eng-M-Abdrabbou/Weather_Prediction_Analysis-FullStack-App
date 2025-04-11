package com.example.weather_backend.exception;

import org.springframework.http.HttpStatus;

// Custom exception for service layer errors
public class WeatherServiceException extends RuntimeException {
    private final HttpStatus status;

    public WeatherServiceException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public WeatherServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}