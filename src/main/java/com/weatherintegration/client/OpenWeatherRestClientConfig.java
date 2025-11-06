package com.weatherintegration.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class OpenWeatherRestClientConfig {

    @Bean
    RestClient openWeatherRestClient(
            @Value("${clients.openweather.weatherBaseUrl}") String baseUrl) {
        return RestClient.builder().baseUrl(baseUrl).build();
    }
}

