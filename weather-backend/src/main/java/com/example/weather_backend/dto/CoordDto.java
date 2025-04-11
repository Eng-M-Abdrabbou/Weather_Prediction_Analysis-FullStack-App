package com.example.weather_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Simple reusable DTO for coordinates
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordDto {
    private double lat;
    private double lon;
    private String name; // Sometimes geocoding includes the name
    private String country; // Sometimes geocoding includes the country
}