package com.example.weather_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

// Maps the response from OpenWeatherMap /data/2.5/weather endpoint
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OwmCurrentWeatherResponseDto {

    private CoordDto coord;
    private List<WeatherDescription> weather;
    private String base;
    private MainInfo main;
    private int visibility;
    private WindInfo wind;
    private RainInfo rain; // Optional
    private SnowInfo snow; // Optional
    private CloudsInfo clouds;
    private long dt; // Time of data calculation, unix, UTC
    private SysInfo sys;
    private int timezone; // Shift in seconds from UTC
    private long id; // City ID
    private String name; // City name
    private int cod; // Internal parameter

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherDescription {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MainInfo {
        private double temp;
        @JsonProperty("feels_like")
        private double feelsLike;
        @JsonProperty("temp_min")
        private double tempMin; // May differ from main temp
        @JsonProperty("temp_max")
        private double tempMax; // May differ from main temp
        private int pressure; // hPa
        private int humidity; // %
        @JsonProperty("sea_level")
        private Integer seaLevel; // Pressure hPa (Optional)
        @JsonProperty("grnd_level")
        private Integer grndLevel; // Pressure hPa (Optional)

    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WindInfo {
        private double speed; // m/s
        private int deg; // direction, degrees
        private Double gust; // m/s (Optional)
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class RainInfo {
        @JsonProperty("1h")
        private Double last1h; // mm
        @JsonProperty("3h")
        private Double last3h; // mm
    }

     @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SnowInfo {
        @JsonProperty("1h")
        private Double last1h; // mm
        @JsonProperty("3h")
        private Double last3h; // mm
    }


    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CloudsInfo {
        private int all; // Cloudiness %
    }

    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SysInfo {
        private Integer type;
        private Long id;
        private String country; // Country code (GB, JP etc.)
        private long sunrise; // unix, UTC
        private long sunset; // unix, UTC
    }
}