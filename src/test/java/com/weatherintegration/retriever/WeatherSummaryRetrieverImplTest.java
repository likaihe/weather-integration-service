package com.weatherintegration.retriever;

import com.weatherintegration.client.OpenWeatherClient;
import com.weatherintegration.dto.CurrentWeather;
import com.weatherintegration.dto.Location;
import com.weatherintegration.exception.NotFoundException;
import com.weatherintegration.exception.OpenWeatherException;
import com.weatherintegration.mapper.WeatherSummaryMapper;
import com.weatherintegration.model.TempUnits;
import com.weatherintegration.model.WeatherSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.HttpClientErrorException;

import static com.weatherintegration.util.Random.randomEnum;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class WeatherSummaryRetrieverImplTest {

    private WeatherSummaryRetrieverImpl weatherSummaryRetriever;
    private OpenWeatherClient openWeatherClient;
    private WeatherSummaryMapper weatherSummaryMapper;

    @BeforeEach
    void setUp() {
        openWeatherClient = Mockito.mock(OpenWeatherClient.class);
        weatherSummaryMapper = Mockito.mock(WeatherSummaryMapper.class);
        weatherSummaryRetriever = new WeatherSummaryRetrieverImpl(openWeatherClient, weatherSummaryMapper);
    }

    @Test
    void shouldRetrieveByLocation() {
        TempUnits tempUnits = randomEnum(TempUnits.class);
        Location location = mock(Location.class);
        CurrentWeather currentWeather = mock(CurrentWeather.class);
        WeatherSummary weatherSummary = mock(WeatherSummary.class);
        //given
        given(openWeatherClient.getByLocation(location)).willReturn(currentWeather);
        given(weatherSummaryMapper.mapFromCurrentWeather(currentWeather,tempUnits)).willReturn(weatherSummary);
        //when
        WeatherSummary result = weatherSummaryRetriever.retrieveByLocation(location, tempUnits);
        //then
        assertThat(result).isEqualTo(weatherSummary);
    }

    @Test
    void shouldThrowNotFound() {
        TempUnits tempUnits = randomEnum(TempUnits.class);
        Location location = mock(Location.class);
        //given
        given(openWeatherClient.getByLocation(location)).willThrow(HttpClientErrorException.NotFound.class);
        //then
        assertThatThrownBy(()-> weatherSummaryRetriever.retrieveByLocation(location, tempUnits))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void shouldThrowOpenWeatherException() {
        TempUnits tempUnits = randomEnum(TempUnits.class);
        Location location = mock(Location.class);
        //given
        given(openWeatherClient.getByLocation(location)).willThrow(RuntimeException.class);
        //then
        assertThatThrownBy(()-> weatherSummaryRetriever.retrieveByLocation(location, tempUnits))
                .isInstanceOf(OpenWeatherException.class);
    }
}