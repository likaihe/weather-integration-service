package com.weatherintegration.retriever;

import com.weatherintegration.client.OpenWeatherClient;
import com.weatherintegration.dto.CurrentWeather;
import com.weatherintegration.dto.Location;
import com.weatherintegration.exception.NotFoundException;
import com.weatherintegration.exception.OpenWeatherException;
import com.weatherintegration.mapper.WeatherSummaryMapper;
import com.weatherintegration.model.TempUnits;
import com.weatherintegration.model.WeatherSummary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

@Component
public class WeatherSummaryRetrieverImpl implements WeatherSummaryRetriever {
    private final OpenWeatherClient openWeatherClient;
    private final WeatherSummaryMapper weatherSummaryMapper;

    public WeatherSummaryRetrieverImpl(OpenWeatherClient openWeatherClient, WeatherSummaryMapper weatherSummaryMapper) {
        this.openWeatherClient = openWeatherClient;
        this.weatherSummaryMapper = weatherSummaryMapper;
    }

    public WeatherSummary retrieveByLocation(Location location, TempUnits tempUnits) {
        CurrentWeather currentWeather = getCurrentWeather(location);
        return weatherSummaryMapper.mapFromCurrentWeather(currentWeather, tempUnits);
    }

    private CurrentWeather getCurrentWeather(Location location) {
        try {
            return openWeatherClient.getByLocation(location);
        } catch (HttpClientErrorException.NotFound notFound) {
            throw new NotFoundException("Location not found.");
        } catch (Exception e) {
            throw new OpenWeatherException("Could not retrieve current weather.");
        }
    }
}
