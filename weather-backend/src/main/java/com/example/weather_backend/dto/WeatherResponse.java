package com.example.weather_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherResponse {
    private String city;
    private double temperature; // Celsius
    private String description;
    private double feelsLike;
    private int humidity;
    private double windSpeed; // meter/sec
    private double latitude;  
    private double longitude; 
}