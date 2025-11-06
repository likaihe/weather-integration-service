package com.weatherintegration.validator;

import com.weatherintegration.dto.Location;
import com.weatherintegration.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class LocationValidation {

    private static final String NO_LOCATION_ERROR_MESSAGE = "City or (lat and lon) is required";
    private final CoordinatesValidator coordinatesValidator;

    public LocationValidation(CoordinatesValidator coordinatesValidator) {
        this.coordinatesValidator = coordinatesValidator;
    }

    public void validate(Location location) {
        validate(location.city(), location.lat(), location.lon());
    }


    private void validate(String city, Double lat, Double lon) {
        if (lat != null || lon != null) {
            coordinatesValidator.validate(lat, lon);
        } else {
            validate(city);
        }
    }

    private void validate(String city) {
        if (city == null) {
            throw new BadRequestException(NO_LOCATION_ERROR_MESSAGE);
        }
    }
}
