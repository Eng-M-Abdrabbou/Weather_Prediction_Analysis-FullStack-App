package com.example.weather_backend.service;

import com.example.weather_backend.dto.*;
import com.example.weather_backend.exception.WeatherServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url.current}")
    private String currentApiUrl;

    @Value("${openweathermap.api.url.forecast}")
    private String forecastApiUrl;

    @Value("${openweathermap.api.url.airpollution}")
    private String airPollutionApiUrl;

    @Value("${openweathermap.api.url.geocoding}")
    private String geocodingApiUrl;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // fetchAllWeatherData method remains the same...
    public ComprehensiveWeatherResponseDto fetchAllWeatherData(double lat, double lon, CoordDto geocodingResult) throws WeatherServiceException {
        String logContext = geocodingResult != null && geocodingResult.getName() != null ? geocodingResult.getName() : lat + "," + lon;
        logger.info("Fetching all weather data for {}", logContext);
        try {
            OwmCurrentWeatherResponseDto currentData = getCurrentWeather(lat, lon);
            OwmForecastResponseDto forecastData = getForecast(lat, lon);
            OwmAirPollutionResponseDto airQualityData = getAirQuality(lat, lon);
            OwmAirPollutionResponseDto.AirPollutionData currentAirQuality =
                    (airQualityData != null && airQualityData.getList() != null && !airQualityData.getList().isEmpty())
                            ? airQualityData.getList().get(0)
                            : null;
            String resolvedName = geocodingResult != null ? geocodingResult.getName() : currentData.getName();
            String country = geocodingResult != null ? geocodingResult.getCountry() : currentData.getSys() != null ? currentData.getSys().getCountry() : null;
            int timezoneOffset = currentData.getTimezone();
            long sunrise = currentData.getSys() != null ? currentData.getSys().getSunrise() : 0;
            long sunset = currentData.getSys() != null ? currentData.getSys().getSunset() : 0;
            String searchedCityName = geocodingResult != null ? geocodingResult.getName() : null;
            ComprehensiveWeatherResponseDto.LocationInfo locationInfo = ComprehensiveWeatherResponseDto.LocationInfo.builder()
                    .searchedCity(searchedCityName)
                    .resolvedName(resolvedName)
                    .latitude(lat)
                    .longitude(lon)
                    .country(country)
                    .timezoneOffset(timezoneOffset)
                    .sunrise(sunrise)
                    .sunset(sunset)
                    .build();
            return ComprehensiveWeatherResponseDto.builder()
                    .locationInfo(locationInfo)
                    .current(currentData)
                    .forecastList(forecastData.getList())
                    .airQuality(currentAirQuality)
                    .build();
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Error fetching comprehensive weather data for {}: {} - {}", logContext, e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw mapHttpClientException(e, logContext);
        } catch (RestClientException e) {
            logger.error("RestClient Error fetching comprehensive weather data for {}: {}", logContext, e.getMessage(), e);
            throw new WeatherServiceException("Could not connect to weather service.", HttpStatus.SERVICE_UNAVAILABLE, e);
        } catch (Exception e) {
            logger.error("Unexpected error fetching comprehensive weather data for {}: {}", logContext, e.getMessage(), e);
            // Avoid masking specific WeatherServiceExceptions if they bubble up
            if (e instanceof WeatherServiceException) {
                 throw (WeatherServiceException) e;
            }
            throw new WeatherServiceException("An unexpected error occurred while retrieving weather data.", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }


    /**
     * Fetches latitude and longitude for a given city name using the Geocoding API.
     * Handles URL encoding correctly.
     */
    public CoordDto getCoordinatesForCity(String city) throws WeatherServiceException {
        logger.info("Attempting geocoding for city: {}", city);

        URI uri = UriComponentsBuilder.fromHttpUrl(geocodingApiUrl)
                .queryParam("q", city)
                .queryParam("limit", 1)
                .queryParam("appid", apiKey)
                .build(false) // Build without re-encoding template variables
                .toUri();

        logger.info("Constructed Geocoding URI: {}", uri.toString());

        try {
            OwmGeocodingResponseDto[] response = restTemplate.getForObject(uri, OwmGeocodingResponseDto[].class);

            if (response == null || response.length == 0) {
                logger.warn("Geocoding API returned no results for city: {}", city);
                throw new WeatherServiceException("City not found: " + city, HttpStatus.NOT_FOUND); // Throw specific 404
            }

            OwmGeocodingResponseDto result = response[0];
            logger.info("Geocoding successful for '{}': lat={}, lon={}, name={}, country={}", city, result.getLat(), result.getLon(), result.getName(), result.getCountry());
            return new CoordDto(result.getLat(), result.getLon(), result.getName(), result.getCountry());

        // *** CORRECTED CATCH BLOCK ORDER AND LOGIC ***
        } catch (WeatherServiceException e) {
             // Re-throw the specific WeatherServiceException (like our 404) immediately
             throw e;
        } catch (HttpClientErrorException e) {
            // Handle HTTP errors from RestTemplate specifically
            logger.error("HTTP Error during geocoding for city '{}': {} - {}", city, e.getStatusCode(), e.getResponseBodyAsString(), e);
            throw mapHttpClientException(e, "city '" + city + "'");
        } catch (RestClientException e) {
            // Handle other RestTemplate connection/network errors
            logger.error("RestClient Error during geocoding for city '{}': {}", city, e.getMessage(), e);
            throw new WeatherServiceException("Could not connect to geocoding service.", HttpStatus.SERVICE_UNAVAILABLE, e);
        } catch (Exception e) {
             // Catch any other unexpected exceptions
             logger.error("Unexpected error during geocoding for city '{}': {}", city, e.getMessage(), e);
             throw new WeatherServiceException("An unexpected error occurred during geocoding.", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
        // **********************************************
    }

    // --- Private Helper Methods for API Calls ---
    // (getCurrentWeather, getForecast, getAirQuality, mapHttpClientException)
    // remain the same as the previous corrected version using URI...

    private OwmCurrentWeatherResponseDto getCurrentWeather(double lat, double lon) throws HttpClientErrorException, RestClientException {
        logger.debug("Calling Current Weather API for lat={}, lon={}", lat, lon);
        URI uri = UriComponentsBuilder.fromHttpUrl(currentApiUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .build(false).toUri(); // Use URI
        logger.debug("Current Weather URI: {}", uri);
        OwmCurrentWeatherResponseDto response = restTemplate.getForObject(uri, OwmCurrentWeatherResponseDto.class); // Use URI
        if (response == null) {
             logger.error("Received null response from Current Weather API for lat={}, lon={}", lat, lon);
             throw new RestClientException("Received null response from Current Weather API");
        }
        logger.debug("Successfully received Current Weather API data.");
        return response;
    }

    private OwmForecastResponseDto getForecast(double lat, double lon) throws HttpClientErrorException, RestClientException {
        logger.debug("Calling Forecast API for lat={}, lon={}", lat, lon);
        URI uri = UriComponentsBuilder.fromHttpUrl(forecastApiUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .queryParam("units", "metric")
                .build(false).toUri(); // Use URI
        logger.debug("Forecast URI: {}", uri);
        OwmForecastResponseDto response = restTemplate.getForObject(uri, OwmForecastResponseDto.class); // Use URI
         if (response == null) {
             logger.error("Received null response from Forecast API for lat={}, lon={}", lat, lon);
             throw new RestClientException("Received null response from Forecast API");
         }
         logger.debug("Successfully received Forecast API data.");
         return response;
    }

    private OwmAirPollutionResponseDto getAirQuality(double lat, double lon) { // Removed throws for AQI failure
        logger.debug("Calling Air Pollution API for lat={}, lon={}", lat, lon);
        URI uri = UriComponentsBuilder.fromHttpUrl(airPollutionApiUrl)
                .queryParam("lat", lat)
                .queryParam("lon", lon)
                .queryParam("appid", apiKey)
                .build(false).toUri(); // Use URI
        logger.debug("Air Pollution URI: {}", uri);
        try {
            OwmAirPollutionResponseDto response = restTemplate.getForObject(uri, OwmAirPollutionResponseDto.class); // Use URI
             if (response == null) {
                 logger.warn("Received null response from Air Pollution API for lat={}, lon={}. Treating as unavailable.", lat, lon);
                 return null;
             }
             logger.debug("Successfully received Air Pollution API data.");
             return response;
        } catch (HttpClientErrorException e) {
            logger.error("HTTP Error fetching Air Quality data for lat={}, lon={}: {} - {}. Proceeding without AQI data.",
                         lat, lon, e.getStatusCode(), e.getResponseBodyAsString());
             return null;
        } catch (RestClientException e) {
             logger.error("Network Error fetching Air Quality data for lat={}, lon={}: {}. Proceeding without AQI data.",
                         lat, lon, e.getMessage());
            return null;
        } catch (Exception e) {
             logger.error("Unexpected error fetching Air Quality data for lat={}, lon={}: {}. Proceeding without AQI data.",
                         lat, lon, e.getMessage(), e);
             return null;
        }
    }

    private WeatherServiceException mapHttpClientException(HttpClientErrorException e, String context) {
         HttpStatus status = (HttpStatus) e.getStatusCode();
         String message;
         if (status == HttpStatus.NOT_FOUND) {
             message = "Could not find data for " + context + ". Please check the location/input.";
         } else if (status == HttpStatus.UNAUTHORIZED || status == HttpStatus.FORBIDDEN) {
             message = "Invalid API Key or unauthorized request for " + context + ". Check backend configuration and OWM subscription.";
         } else if (status == HttpStatus.TOO_MANY_REQUESTS) {
             message = "API call limit exceeded for " + context + ". Please wait and try again later or check your OWM plan.";
         } else if (status.is4xxClientError()) {
             message = "Invalid request [" + status.value() + "] for " + context + ". Details: " + e.getResponseBodyAsString();
         } else if (status.is5xxServerError()) {
              message = "Weather service unavailable or encountered an error [" + status.value() + "] while processing request for " + context + ". Please try again later.";
         } else {
             message = "An error occurred [" + status.value() + "] while contacting the weather service for " + context + ".";
         }
         logger.warn("Mapping HttpClientErrorException for context '{}': Status={}, ResponseBody='{}'", context, status, e.getResponseBodyAsString());
         return new WeatherServiceException(message, status, e);
    }
}