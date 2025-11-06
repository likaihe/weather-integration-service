package com.weatherintegration.delegate;

import com.weatherintegration.LocationFactory;
import com.weatherintegration.api.WeatherApiDelegate;
import com.weatherintegration.dto.Location;
import com.weatherintegration.model.TempUnits;
import com.weatherintegration.model.WeatherSummary;
import com.weatherintegration.retriever.WeatherSummaryRetriever;
import com.weatherintegration.validator.LocationValidation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WeatherApiDelegateImpl implements WeatherApiDelegate {
    private final LocationValidation locationValidation;
    private final WeatherSummaryRetriever weatherSummaryRetriever;
    private final LocationFactory locationFactory;

    public WeatherApiDelegateImpl(LocationValidation locationValidation,
                                  WeatherSummaryRetriever weatherSummaryRetriever,
                                  LocationFactory locationFactory) {
        this.locationValidation = locationValidation;
        this.weatherSummaryRetriever = weatherSummaryRetriever;
        this.locationFactory = locationFactory;
    }

    @Override
    public ResponseEntity<WeatherSummary> getWeatherSummary(String city, Double lat, Double lon, TempUnits tempUnits) {
        Location location = locationFactory.create(city, lat, lon);
        locationValidation.validate(location);
        return ResponseEntity.ok(weatherSummaryRetriever.retrieveByLocation(location, tempUnits));
    }
}
