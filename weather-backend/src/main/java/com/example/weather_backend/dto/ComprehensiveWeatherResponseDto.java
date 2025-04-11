package com.example.weather_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// MODIFIED: This is the final DTO structure sent to the React Frontend
// Uses data from /weather, /forecast, /air_pollution APIs
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ComprehensiveWeatherResponseDto {

    private LocationInfo locationInfo;
    private OwmCurrentWeatherResponseDto current; // Data from /weather endpoint
    private List<OwmForecastResponseDto.ForecastItem> forecastList; // List of 3-hour forecasts from /forecast
    private OwmAirPollutionResponseDto.AirPollutionData airQuality; // Data from /air_pollution endpoint

    // NOTE: Alerts are not typically part of the /forecast API response, so removed for now.

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationInfo {
        private String searchedCity;
        private String resolvedName;
        private double latitude;
        private double longitude;
        private String country;
        private String state;
        private String timezoneId; // Can be derived if needed, OWM provides offset
        private int timezoneOffset; // In seconds from UTC
        private long sunrise; // From current weather or forecast city info
        private long sunset;  // From current weather or forecast city info
    }
}