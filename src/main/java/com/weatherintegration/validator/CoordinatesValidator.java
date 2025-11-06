package com.weatherintegration.validator;

import com.weatherintegration.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class CoordinatesValidator {
    private static final double LAT_MIN = -90.0;
    private static final double LAT_MAX = 90.0;
    private static final double LON_MIN = -180.0;
    private static final double LON_MAX = 180.0;


    public void validate(Double lat, Double lon) {
        validateLat(lat);
        validateLon(lon);
    }

    private void validateLon(Double lon) {
        if (lon == null) throw new BadRequestException("longitude is required");
        if (lon < LON_MIN || lon > LON_MAX)
            throw new BadRequestException("longitude must be between -180 and 180");
    }

    private void validateLat(Double lat) {
        if (lat == null) throw new BadRequestException("latitude is required");
        if (lat < LAT_MIN || lat > LAT_MAX)
            throw new BadRequestException("latitude must be between -90 and 90");
    }
}
