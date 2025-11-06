package com.weatherintegration.mapper;

import com.weatherintegration.dto.CurrentWeather;
import com.weatherintegration.model.TempUnits;
import com.weatherintegration.model.WeatherSummary;
import org.mapstruct.*;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = "spring")
public interface WeatherSummaryMapper {
    @Mappings({
            @Mapping(target = "jobsite", source = "name"),
            @Mapping(target = "temperatureC", source = "main.temp", qualifiedByName = "kelvinToC"),
            @Mapping(target = "temperatureF", source = "main.temp", qualifiedByName = "kelvinToF"),
            @Mapping(target = "condition", source = "weather", qualifiedByName = "firstDescription"),
            @Mapping(target = "windSpeedKph", source = "wind.speed", qualifiedByName = "mpsToKph"),
            @Mapping(target = "updatedAt", source = "dt", qualifiedByName = "secToOffset"),
            @Mapping(target = "isHotDay", source = "main.temp", qualifiedByName = "isHotDay"),
            @Mapping(target = "isGoodForConcretePouring", source = ".", qualifiedByName = "isGoodForConcretePouring")
    })
    WeatherSummary mapFromCurrentWeather(CurrentWeather currentWeather, @Context TempUnits tempUnits);

    @Named("firstDescription")
    default String firstDescription(List<CurrentWeather.Weather> weathers) {
        return (weathers != null && !weathers.isEmpty()) ? weathers.get(0).description() : null;
    }

    @Named("kelvinToC")
    default Double KelvinToC(double kelvin, @Context TempUnits units) {
        return TempUnits.C.equals(units) ? kelvin - 273.15 : null;
    }

    @Named("kelvinToF")
    default Double kelvinToF(double kelvin, @Context TempUnits units) {
        return TempUnits.F.equals(units) ? (kelvin - 273.15) * 9 / 5 + 32 : null;
    }

    @Named("isHotDay")
    default boolean isHotDay(double kelvin) {
        return kelvin > 303.15;
    }

    @Named("mpsToKph")
    default double mpsToKph(double mps) {
        return mps * 3.6;
    }

    @Named("secToOffset")
    default OffsetDateTime secToOffset(long epochSec) {
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(epochSec), ZoneOffset.UTC);
    }

    @Named("isGoodForConcretePouring")
    default Boolean isGoodForConcretePouring(CurrentWeather currentWeather) {
        List<CurrentWeather.Weather> weathers = currentWeather.weather();
        if (weathers == null || weathers.isEmpty()) {
            return null;
        }
        if (currentWeather.main() == null || currentWeather.main().temp() == null) {
            return null;
        }
        return !weathers.getFirst().main().equals("Rain") && currentWeather.main().temp() > 283.15;
    }
}
