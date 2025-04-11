

# Weather Application Backend

This repository contains the Spring Boot backend service for the Weather Application. It provides a RESTful API to fetch current weather conditions, 5-day/3-hour forecasts, and air quality information for a given location using the OpenWeatherMap API.

## Features

*   **REST API:** Exposes endpoints to retrieve combined weather data.
*   **Geocoding:** Converts city names to geographical coordinates (latitude/longitude).
*   **OpenWeatherMap Integration:** Fetches data for:
    *   Current Weather (`/data/2.5/weather`)
    *   5-day/3-hour Forecast (`/data/2.5/forecast`)
    *   Air Pollution/AQI (`/data/2.5/air_pollution`)
*   **Data Aggregation:** Combines data from multiple OWM endpoints into a single response for the frontend.

## Technologies Used

*   **Java 17+** (Developed using 21)
*   **Spring Boot 3.x** (Web, Lombok)
*   **Maven** (Build Tool & Dependency Management)
*   **RestTemplate** (For making HTTP requests to OWM)
*   **Jackson** (For JSON serialization/deserialization - included with Spring Web)
*   **Lombok** (To reduce boilerplate code)
*   **SLF4J / Logback** (Logging - included with Spring Boot)

## Prerequisites

*   **Java Development Kit (JDK):** Version 17 or later installed.
*   **Maven:** Apache Maven build tool installed.
*   **OpenWeatherMap API Key:** A valid API key obtained from [https://openweathermap.org/](https://openweathermap.org/). The free tier should suffice for the APIs used in this version.

## Setup

1.  **Clone the repository:**
    ```bash
    git clone <your-backend-repo-url>
    cd weather-backend
    ```

2.  **Configure API Key:**
    *   Navigate to `src/main/resources/`.
    *   Open the `application.properties` file.
    *   Find the line `openweathermap.api.key=YOUR_API_KEY`.
    *   **Replace `YOUR_API_KEY` with your actual OpenWeatherMap API key.**
    *   Save the file.

3.  **(Optional) Build the project:**
    ```bash
    ./mvnw clean package
    # Or on Windows: mvnw.cmd clean package
    ```
    This will compile the code and create an executable JAR file in the `target/` directory.

## Running the Application

There are two main ways to run the backend:

1.  **Using Maven Spring Boot Plugin:**
    ```bash
    ./mvnw spring-boot:run
    # Or on Windows: mvnw.cmd spring-boot:run
    ```

2.  **Running the JAR file (after building):**
    ```bash
    java -jar target/weather-backend-0.0.1-SNAPSHOT.jar
    # Replace with the actual JAR file name if different
    ```

The backend server will start, typically on port **8081** (as configured in `application.properties`). You should see log output indicating the application has started.

## API Endpoint

The primary endpoint provided by this backend is:

*   **`GET /api/weather/location`**: Fetches comprehensive weather data.
    *   **Query Parameters:**
        *   `city` (String, optional): Name of the city (e.g., `city=London`, `city=Abu%20Dhabi`).
        *   `lat` (Double, optional): Latitude.
        *   `lon` (Double, optional): Longitude.
        *   *(Note: Provide either `city` OR both `lat` and `lon`)*
    *   **Success Response (200 OK):** Returns a JSON object (`ComprehensiveWeatherResponseDto`) containing:
        *   `locationInfo`: Details about the location (resolved name, coordinates, country, timezone offset, sunrise/sunset).
        *   `current`: Current weather details (temperature, conditions, wind, humidity, etc.).
        *   `forecastList`: An array of forecast objects, typically for 5 days with 3-hour intervals.
        *   `airQuality`: Air quality index and component data (may be null if unavailable).
    *   **Error Responses:** Returns standard HTTP error codes (e.g., 400 Bad Request, 404 Not Found, 500 Internal Server Error) with a JSON error message.
    


## Description of Directories and Files

This directory contains the Spring Boot backend application responsible for:

* Receiving requests from the frontend.
* Fetching weather data from external APIs (like OpenWeatherMap).
* Processing and formatting the data.
* Sending the processed data back to the frontend.

    * **`pom.xml`**: Maven's project object model file, defining project dependencies, build configurations, etc.
    * **`src/main/java/com/example/weather_backend/`**: Contains the main Java source code for the backend.
        * **`controller/`**: Contains REST controllers that handle incoming API requests.
            * **`WeatherController.java`**: Exposes endpoints for fetching weather data (e.g., current weather, forecast, air quality).
        * **`dto/`**: Contains Data Transfer Objects used for structuring request and response data. These DTOs often map to the structure of external API responses or define the format of data exchanged with the frontend.
            * **`ComprehensiveWeatherResponseDto.java`**: Likely combines data from multiple OpenWeatherMap API responses into a single, more convenient object for the frontend.
            * **`CoordDto.java`**: Represents geographical coordinates (latitude and longitude).
            * **`OpenWeatherMapResponse.java`**: Could be a base class or interface for handling responses from the OpenWeatherMap API.
            * **`OwmAirPollutionResponseDto.java`**: Represents the structure of the air pollution data received from OpenWeatherMap.
            * **`OwmCurrentWeatherResponseDto.java`**: Represents the structure of the current weather data received from OpenWeatherMap.
            * **`OwmForecastResponseDto.java`**: Represents the structure of the weather forecast data received from OpenWeatherMap.
            * **`OwmGeocodingResponseDto.java`**: Represents the structure of the geocoding data (converting location names to coordinates) from OpenWeatherMap.
            * **`OwmOneCallApiResponseDto.java`**: Represents the structure of the response from OpenWeatherMap's One Call API, which provides various weather data points in a single request.
            * **`WeatherResponse.java`**: A more generic DTO for weather-related information, potentially used for simpler responses.
        * **`exception/`**: Contains custom exception classes used within the backend.
            * **`WeatherServiceException.java`**: A custom exception specifically for errors occurring within the weather service logic.
        * **`service/`**: Contains service classes that handle the business logic of the application.
            * **`WeatherService.java`**: Contains methods to interact with external weather APIs, process the data, and return it to the controller.
        * **`WeatherBackendApplication.java`**: The main entry point for the Spring Boot application, annotated with `@SpringBootApplication`.
    * **`src/main/resources/`**: Contains application configuration files (e.g., `application.properties` or `application.yml`).
    * **`src/test/java/com/example/weather_backend/`**: Contains unit and integration tests for the backend application.

## Project Structure (Key Files/Dirs)

weather-backend/
├── pom.xml # Maven configuration
└── src/
├── main/
│ ├── java/
│ │ └── com/example/weatherbackend/
│ │ ├── controller/ # API request handling (WeatherController)
│ │ ├── dto/ # Data Transfer Objects
│ │ ├── exception/ # Custom exceptions
│ │ ├── service/ # Business logic (WeatherService)
│ │ └── WeatherBackendApplication.java # Main application class
│ └── resources/
│ └── application.properties # Configuration (API Key, URLs, Port)
└── test/ # Unit/Integration tests (if added)

## Future Improvements

*   Implement caching for OWM API calls to reduce usage and improve response time.
*   Add more robust error handling and input validation.
*   Integrate unit and integration tests.
*   Secure the API key using environment variables or a secrets manager instead of `application.properties` for production.
*   Consider switching to `WebClient` for non-blocking API calls.