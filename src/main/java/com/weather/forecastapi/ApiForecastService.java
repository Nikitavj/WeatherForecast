package com.weather.forecastapi;

import com.weather.location.LocationDto;

import java.util.List;

public interface ApiForecastService {

    List<LocationDto> searchLocationByCityName(String name);

    ForecastDto searchForecastByLocation(LocationDto location);

}
