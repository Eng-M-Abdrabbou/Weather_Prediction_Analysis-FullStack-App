package com.example.weather_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// Maps the response from OpenWeatherMap /data/2.5/forecast endpoint
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwmForecastResponseDto {
    private String cod; // Internal parameter
    private int message; // Internal parameter
    private int cnt; // Number of forecast items returned
    private List<ForecastItem> list;
    private CityInfo city;

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ForecastItem {
        private long dt; // Time of data forecasted, unix, UTC
        private OwmCurrentWeatherResponseDto.MainInfo main; // Reusing MainInfo DTO
        private List<OwmCurrentWeatherResponseDto.WeatherDescription> weather; // Reusing WeatherDescription DTO
        private OwmCurrentWeatherResponseDto.CloudsInfo clouds; // Reusing CloudsInfo DTO
        private OwmCurrentWeatherResponseDto.WindInfo wind; // Reusing WindInfo DTO
        private int visibility;
        private double pop; // Probability of precipitation (0-1)
        private OwmCurrentWeatherResponseDto.RainInfo rain; // Optional, contains "3h" typically
        private OwmCurrentWeatherResponseDto.SnowInfo snow; // Optional, contains "3h" typically
        private SysPart sys; // Contains "pod" (part of day n/d)
        @JsonProperty("dt_txt")
        private String dtTxt; // Data/time string e.g., "2023-11-15 18:00:00"
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SysPart {
        private String pod; // Part of day (n - night, d - day)
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CityInfo {
        private long id;
        private String name;
        private CoordDto coord; // Reusing CoordDto
        private String country;
        private int population;
        private int timezone; // Shift in seconds from UTC
        private long sunrise; // unix, UTC
        private long sunset; // unix, UTC
    }
}