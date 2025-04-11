package com.example.weather_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Map;

// Maps the response from OpenWeatherMap Air Pollution API
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwmAirPollutionResponseDto {
    private CoordDto coord; // Coordinates might be returned
    private List<AirPollutionData> list;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AirPollutionData {
        private long dt; // Timestamp
        private MainInfo main;
        private Map<String, Double> components; // e.g., {"co": 201.94, "no": 0.02, ...}
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MainInfo {
        private int aqi; // Air Quality Index (1-5)
    }
}