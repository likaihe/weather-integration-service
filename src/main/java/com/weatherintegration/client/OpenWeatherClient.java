package com.weatherintegration.client;

import com.weatherintegration.dto.CurrentWeather;
import com.weatherintegration.dto.Location;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
public class OpenWeatherClient {

    private final RestClient restClient;
    private final String apiKey;

    public OpenWeatherClient(
            RestClient openWeatherRestClient,
            @Value("${clients.openweather.apikey}") String apiKey) {
        this.restClient = openWeatherRestClient;
        this.apiKey = apiKey;
    }

    public CurrentWeather getByLocation(Location location) {
        return restClient.get()
                .uri(builder -> builder
                        .queryParamIfPresent("lat", Optional.ofNullable(location.lat()))
                        .queryParamIfPresent("lon", Optional.ofNullable(location.lon()))
                        .queryParamIfPresent("q", Optional.ofNullable(location.city()))
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .body(CurrentWeather.class);
    }
}
