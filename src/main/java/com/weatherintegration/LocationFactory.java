package com.weatherintegration;

import com.weatherintegration.dto.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationFactory {

    public Location create(String city, Double lat, Double lon) {
        return new Location(city,lat,lon);
    }
}
