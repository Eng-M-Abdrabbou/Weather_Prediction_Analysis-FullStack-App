package com.example.weather_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

// Maps the response from OpenWeatherMap Geocoding API (Direct)
// Note: This API returns an array, we'll usually take the first result.
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwmGeocodingResponseDto {
    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state; // Optional state info
}