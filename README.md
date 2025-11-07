# Weather Integration Service

A Spring Boot service that Integrate with **OpenWeather** and returns a normalized `WeatherSummary`.

---

## Requirements

- **Java 21+**
- **Gradle** (wrapper provided: `./gradlew`)
- **OpenWeather API key** (`OPENWEATHER_APIKEY`)

---

## Configuration
Provide the API key via an environment variable, or via a local profile file.
example of run command using ./gradlew
`OPENWEATHER_APIKEY="your-key" ./gradlew bootRun` or `SPRING_PROFILES_ACTIVE=local ./gradlew bootRun`

## Build & Test
### Run unit tests
./gradlew test

### Build the jar
./gradlew clean build

## API Access
the default port is 8080

- **Swagger UI:**  
  after startup success locally: http://localhost:8080/swagger-ui/index.html

- **OpenAPI spec:**  
  See `src/main/resources/openapi/api.yml` for the full contract and endpoint details.

improvements:
need logs,more Error Handling,tracing and etc, forgot to round the double clean up build.gradle

assumptions
- this api is open for public
- when both city and lat and log provided we use city