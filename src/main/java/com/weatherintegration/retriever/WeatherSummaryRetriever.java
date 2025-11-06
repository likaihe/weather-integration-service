package com.weatherintegration.retriever;

import com.weatherintegration.dto.Location;
import com.weatherintegration.model.TempUnits;
import com.weatherintegration.model.WeatherSummary;

public interface WeatherSummaryRetriever {
    WeatherSummary retrieveByLocation(Location location, TempUnits tempUnits);
}
