# Java Full Stack Weather Forecast and Analysis Application

This project is a simple weather forecast and analysis application featuring a React frontend and a Spring Boot backend. It allows users to search for weather by city and displays current conditions, a 5-day/3-hour forecast, air quality index, and a location map.

## Architecture

This application follows a standard **Client-Server** architecture:

1.  **Backend (`weather-backend` directory):** A Java Spring Boot application that serves a RESTful API. It handles business logic, interacts with the external OpenWeatherMap API to fetch weather data, processes it, and sends it back to the frontend.
2.  **Frontend (`weather-frontend` directory):** A React single-page application (SPA) that runs in the user's browser. It provides the user interface, captures user input, communicates with the backend API via HTTP requests (using Axios), and displays the received weather data.

## Features

*   **City-based Weather Search**
*   **Current Weather Display:** Temperature, conditions, feels like, wind, humidity, pressure, visibility, sunrise/sunset.
*   **5-Day / 3-Hour Forecast:** Shows temperature, conditions, precipitation probability, and wind for upcoming intervals.
*   **Air Quality Index (AQI):** Displays the current AQI level and major pollutant components (if available).
*   **Interactive Location Map (Leaflet):** Shows the geographical location of the searched city.
*   **Responsive UI:** Adapts to different screen sizes.

## Screenshots


### Weather Information
![Weather Info](/Images/WthrInfo.png)

### Map View
![Map](/Images/Map.png)

### Hourly Forecasting
![Hourly Forecasting](/Images/HrForecast.png)

### Air Quality Index
![Air Quality Index](/Images/AQI.png)



## Technologies Used

**Backend (`weather-backend`):**
*   Java 17+ (Developed using 21 for compaitibilty)
*   Spring Boot 3.4.4 (Web, Lombok)
*   Maven
*   RestTemplate
*   Jackson, Lombok, SLF4J/Logback

**Frontend (`weather-frontend`):**
*   React 18+
*   JavaScript (ES6+)
*   CSS3 (Flexbox, Grid)
*   Axios
*   React Context API
*   React Leaflet / Leaflet.js
*   Create React App

**External Service:**
*   OpenWeatherMap API (Free Tier)

## Prerequisites

*   **Java Development Kit (JDK):** Version 17 or later.
*   **Maven:** Apache Maven build tool.
*   **Node.js and npm (or yarn):** Node.js version 16.x or later recommended.
*   **OpenWeatherMap API Key:** A valid API key from [https://openweathermap.org/](https://openweathermap.org/).


## Description of Directories and Files

### `weather-backend/`

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

### `weather-frontend/`

This directory contains the React frontend application responsible for:

* Providing the user interface for interacting with the weather application.
* Making API calls to the backend to fetch weather data.
* Displaying the weather information to the user.

    * **`.gitignore`**: Specifies files that Git should ignore in this frontend project.
    * **`README.md`**: A README file specific to the frontend, potentially containing instructions on running the frontend development server.
    * **`package.json`**: Contains metadata about the frontend application, including dependencies and scripts for development, building, and testing.
    * **`package-lock.json`**: Records the exact versions of dependencies used in the frontend.
    * **`node_modules/`**: Contains the installed Node..js packages (dependencies) for the frontend. This directory is usually not committed to Git.
    * **`public/`**: Contains static assets served directly by the frontend, such as the main HTML file (`index.html`), images, and other public resources.
    * **`src/`**: Contains the main source code for the React frontend.
        * **`App.js`**: The root component of the React application, responsible for setting up the main layout and routing (if any).
        * **`App.css`**: Global CSS styles for the application.
        * **`index.js`**: The entry point that renders the `App` component into the DOM.
        * **`index.css`**: Global CSS styles applied to the `index.js` file.
        * **`WeatherDataContext.js`**: Implements the React Context API to manage and share weather-related state (data, loading status, errors) across different components.
        * **`components/`**: Contains reusable UI components used throughout the application.
            * **`AirQuality.js`**: Component to display air quality information (e.g., pollutants, AQI).
            * **`CurrentWeather.js`**: Component to display the current weather conditions (temperature, humidity, wind, etc.).
            * **`Forecast.js`**: Component to display the weather forecast for the coming days or hours.
            * **`SearchBar.js`**: Component that provides an input field for users to enter a location to search for.
            * **`WeatherMap.js`**: Component that likely displays a map with weather-related overlays (if this feature is implemented).
        * **`... (other source files)`**: May include utility functions, custom hooks, or additional components.

### Root `README.md`

This file (the one you are currently reading the description of) provides a high-level overview of the entire full-stack project, including both the backend and frontend components, and instructions on how to set up and run the application.

## Project Structure

```

fullstack-weather-app/ (Root Directory)
├── weather-backend/ # Spring Boot Backend Code
│ ├── pom.xml
│ ├── src/
│ │   ├── main/
│ │   │   ├── java/
│ │   │   │   └── com/example/weather_backend/
│ │   │   │       ├── controller/
│ │   │   │       │   └── WeatherController.java
│ │   │   │       ├── dto/
│ │   │   │       │   ├── ComprehensiveWeatherResponseDto.java
│ │   │   │       │   ├── CoordDto.java
│ │   │   │       │   ├── OpenWeatherMapResponse.java
│ │   │   │       │   ├── OwmAirPollutionResponseDto.java
│ │   │   │       │   ├── OwmCurrentWeatherResponseDto.java
│ │   │   │       │   ├── OwmForecastResponseDto.java
│ │   │   │       │   ├── OwmGeocodingResponseDto.java
│ │   │   │       │   ├── OwmOneCallApiResponseDto.java
│ │   │   │       │   └── WeatherResponse.java
│ │   │   │       ├── exception/
│ │   │   │       │   └── WeatherServiceException.java
│ │   │   │       └── service/
│ │   │   │           └── WeatherService.java
│ │   │   │       ├── WeatherBackendApplication.java
│ │   │   └── resources/
│ │   └── test/
│ │       └── java/
│ │           └── com/example/weather_backend/
├── weather-frontend/ # React Frontend Code
│ ├── .gitignore
│ ├── README.md
│ ├── package.json
│ ├── package-lock.json
│ ├── node_modules/
│ ├── public/
│ │   └── ... (public files)
│ └── src/
│     ├── App.js
│     ├── App.css
│     ├── index.js
│     ├── index.css
│     ├── WeatherDataContext.js
│     ├── components/
│     │   ├── AirQuality.js
│     │   ├── CurrentWeather.js
│     │   ├── Forecast.js
│     │   ├── SearchBar.js
│     │   └── WeatherMap.js
│     └── ... (other source files)
└── README.md # Project Root Readme

```

## Setup

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Eng-M-Abdrabbou/Weather_Prediction_Analysis-FullStack-App.git
    cd fullstack-weather-app
    ```

2.  **Configure Backend API Key:**
    *   Navigate into the `weather-backend` directory: `cd weather-backend`
    *   Go to `src/main/resources/`.
    *   Open `application.properties`.
    *   Replace `YOUR_API_KEY` in `openweathermap.api.key=YOUR_API_KEY` with your actual OWM key.
    *   Save the file.
    *   Return to the root directory: `cd ..`

3.  **Install Frontend Dependencies:**
    *   Navigate into the `weather-frontend` directory: `cd weather-frontend`
    *   Run the installation command:
        ```bash
        npm install
        # or: yarn install
        ```
    *   Return to the root directory: `cd ..`

## Running the Application

The backend and frontend must be run separately.

1.  **Start the Backend:**
    *   Open a terminal in the **`weather-backend`** directory.
    *   Run the Spring Boot application:
        ```bash
        ./mvnw spring-boot:run
        # Or on Windows: mvnw.cmd spring-boot:run
        ```
    *   Wait for the log output indicating the server has started on port 8081.

2.  **Start the Frontend:**
    *   Open a **separate, new** terminal in the **`weather-frontend`** directory.
    *   Run the React development server:
        ```bash
        npm start
        # or: yarn start
        ```
    *   This should automatically open `http://localhost:3000` in your browser.

Now you can use the application in your browser. The frontend (on port 3000) will make requests to the backend (on port 8081).

## API Key Security Note

For demonstration purposes, the API key is placed in `application.properties`.
