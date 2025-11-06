package com.weatherintegration.dto;

import java.util.List;

/**
 *
 * @param name    City name
 * @param main
 * @param weather
 * @param wind
 * @param dt      Time of data calculation, unix, UTC
 */
public record CurrentWeather(
        String name,
        Main main,
        List<Weather> weather,
        Wind wind,
        long dt
) {
    /**
     *
     * @param temp Temperature. Unit Default: Kelvin, Metric: Celsius, Imperial: Fahrenheit
     */
    public record Main(Double temp) {
    }

    /**
     *
     * @param description Weather condition within the group. eg. moderate rain
     * @param main        Group of weather parameters. eg. Rain
     */
    public record Weather(String description, String main) {
    }

    /**
     *
     * @param speed Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour
     */
    public record Wind(Double speed) {
    }
}
