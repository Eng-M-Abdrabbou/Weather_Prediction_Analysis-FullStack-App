package com.example.weather_backend.controller;

import com.example.weather_backend.dto.ComprehensiveWeatherResponseDto;
import com.example.weather_backend.dto.CoordDto;
import com.example.weather_backend.exception.WeatherServiceException;
import com.example.weather_backend.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin(origins = "http://localhost:3000") // Adjust for production
public class WeatherController {

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/location")
    public ResponseEntity<ComprehensiveWeatherResponseDto> getComprehensiveWeather(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon) {

        logger.info("Received weather request - City: '{}', Lat: {}, Lon: {}", city, lat, lon);

        boolean hasCity = city != null && !city.trim().isEmpty();
        boolean hasCoords = lat != null && lon != null;

        if (!hasCity && !hasCoords) {
             logger.warn("Bad request: Missing city or coordinates.");
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please provide either a 'city' name or 'lat' and 'lon' coordinates.");
        }
        // Optional: Prioritize coordinates if both are provided
        // if (hasCity && hasCoords) { logger.debug("Both city and coords provided, using coordinates."); }

        try {
            ComprehensiveWeatherResponseDto response;
            if (hasCoords) {
                logger.debug("Fetching weather using coordinates: lat={}, lon={}", lat, lon);
                // Pass null for geocodingResult as we started with coordinates
                response = weatherService.fetchAllWeatherData(lat, lon, null);
                // Enrich basic location info if needed (if service didn't fully populate)
                if (response.getLocationInfo() != null && response.getLocationInfo().getResolvedName() == null) {
                     response.getLocationInfo().setResolvedName(String.format("Coordinates [%.2f, %.2f]", lat, lon));
                }
            } else {
                // Fetch using city name (requires geocoding first)
                logger.debug("Fetching weather using city: {}", city);
                CoordDto coords = weatherService.getCoordinatesForCity(city); // Get coords AND resolved name/country
                // Pass the full geocoding result to the main fetch method
                response = weatherService.fetchAllWeatherData(coords.getLat(), coords.getLon(), coords);
            }
            logger.info("Successfully fetched weather data for request.");
            return ResponseEntity.ok(response);

        } catch (WeatherServiceException e) {
             logger.error("WeatherServiceException caught in controller: {} (Status: {})", e.getMessage(), e.getStatus());
             throw new ResponseStatusException(e.getStatus(), e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Unexpected error in getComprehensiveWeather: {}", e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected internal error occurred.", e);
        }
    }
}