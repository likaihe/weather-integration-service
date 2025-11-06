package com.weatherintegration.delegate;

import com.weatherintegration.LocationFactory;
import com.weatherintegration.dto.Location;
import com.weatherintegration.model.TempUnits;
import com.weatherintegration.model.WeatherSummary;
import com.weatherintegration.retriever.WeatherSummaryRetriever;
import com.weatherintegration.validator.LocationValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.weatherintegration.util.Random.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class WeatherApiDelegateImplTest {

    private WeatherApiDelegateImpl weatherApiDelegate;
    @Mock
    private LocationValidation locationValidation;

    @Mock
    private WeatherSummaryRetriever weatherSummaryRetriever;
    @Mock
    private LocationFactory locationFactory;


    @BeforeEach
    void setUp() {
        weatherApiDelegate = new WeatherApiDelegateImpl(locationValidation, weatherSummaryRetriever, locationFactory);
    }

    @Test
    void shouldGetWeatherSummary() {
        WeatherSummary weatherSummary = mock(WeatherSummary.class);
        Location location = mock(Location.class);
        String city = randomString();
        TempUnits tempUnits = randomEnum(TempUnits.class);
        Double lat = randomDouble();
        Double lon = randomDouble();
        //given
        given(locationFactory.create(city, lat, lon)).willReturn(location);
        given(weatherSummaryRetriever.retrieveByLocation(location,tempUnits)).willReturn(weatherSummary);
        //when
        ResponseEntity<WeatherSummary> result = weatherApiDelegate.getWeatherSummary(city, lat, lon, tempUnits);
        //then
        then(locationValidation).should().validate(location);
        assertThat(result.getBody()).isEqualTo(weatherSummary);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}