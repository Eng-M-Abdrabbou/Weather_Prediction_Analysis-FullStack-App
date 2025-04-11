package com.example.weather_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// Maps the complex response from OpenWeatherMap One Call API 3.0
// Includes current, minutely (optional), hourly, daily, alerts (optional)
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwmOneCallApiResponseDto {
    private double lat;
    private double lon;
    private String timezone;
    @JsonProperty("timezone_offset")
    private int timezoneOffset; // In seconds from UTC

    private CurrentWeather current;
    // private List<MinutelyForecast> minutely; // Often not needed for basic forecast
    private List<HourlyForecast> hourly;
    private List<DailyForecast> daily;
    private List<WeatherAlert> alerts;

    // Nested class for Current Weather section
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrentWeather {
        private long dt; // Current time, Unix, UTC
        private long sunrise;
        private long sunset;
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        private int pressure; // hPa
        private int humidity; // %
        @JsonProperty("dew_point")
        private double dewPoint;
        private double uvi; // UV index
        private int clouds; // Cloudiness, %
        private int visibility; // Average visibility, metres
        @JsonProperty("wind_speed")
        private double windSpeed; // m/s
        @JsonProperty("wind_deg")
        private int windDeg; // Wind direction, degrees
        @JsonProperty("wind_gust")
        private Double windGust; // Optional wind gust
        private List<WeatherDescription> weather;
        private Rain rain; // Optional rain info
        private Snow snow; // Optional snow info
    }

    // Nested class for Weather Description (used in multiple sections)
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherDescription {
        private int id;
        private String main; // e.g., "Clouds"
        private String description; // e.g., "overcast clouds"
        private String icon; // Weather icon id
    }

    // Optional nested class for Rain info
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rain {
        @JsonProperty("1h") // Rain volume for the last 1 hour, mm
        private Double last1h;
    }

    // Optional nested class for Snow info
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Snow {
        @JsonProperty("1h") // Snow volume for the last 1 hour, mm
        private Double last1h;
    }

    /* // Optional Nested class for Minutely Forecast
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MinutelyForecast {
        private long dt;
        private double precipitation; // Precipitation volume, mm
    } */

    // Nested class for Hourly Forecast
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HourlyForecast {
        private long dt;
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        private int pressure;
        private int humidity;
        @JsonProperty("dew_point")
        private double dewPoint;
        private double uvi;
        private int clouds;
        private int visibility;
        @JsonProperty("wind_speed")
        private double windSpeed;
        @JsonProperty("wind_deg")
        private int windDeg;
        @JsonProperty("wind_gust")
        private Double windGust;
        private List<WeatherDescription> weather;
        private double pop; // Probability of precipitation (0-1)
        private Rain rain; // Optional rain info for the hour
        private Snow snow; // Optional snow info for the hour
    }

    // Nested class for Daily Forecast
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DailyForecast {
        private long dt;
        private long sunrise;
        private long sunset;
        private long moonrise;
        private long moonset;
        @JsonProperty("moon_phase")
        private double moonPhase;
        private String summary; // Human-readable summary (may not always be present)
        private Temp temp;
        @JsonProperty("feels_like")
        private FeelsLike feelsLike;
        private int pressure;
        private int humidity;
        @JsonProperty("dew_point")
        private double dewPoint;
        @JsonProperty("wind_speed")
        private double windSpeed;
        @JsonProperty("wind_deg")
        private int windDeg;
        @JsonProperty("wind_gust")
        private Double windGust;
        private List<WeatherDescription> weather;
        private int clouds;
        private double pop; // Probability of precipitation (0-1)
        private Double rain; // Rain volume for the day, mm (optional)
        private Double snow; // Snow volume for the day, mm (optional)
        private double uvi;
    }

    // Nested class for Daily Temperature details
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Temp {
        private double day;
        private double min;
        private double max;
        private double night;
        private double eve; // Evening
        private double morn; // Morning
    }

    // Nested class for Daily Feels Like temperatures
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FeelsLike {
        private double day;
        private double night;
        private double eve;
        private double morn;
    }

    // Nested class for Weather Alerts
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherAlert {
        @JsonProperty("sender_name")
        private String senderName;
        private String event; // e.g., "High Wind Warning"
        private long start; // Alert start time, Unix, UTC
        private long end; // Alert end time, Unix, UTC
        private String description;
        private List<String> tags; // Optional tags categorizing the alert
    }
}