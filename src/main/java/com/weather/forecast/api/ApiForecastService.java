package com.weather.forecast.api;

import com.weather.forecast.ForecastDto;
import com.weather.location.LocationDto;

import java.util.List;

public interface ApiForecastService {

    List<LocationDto> searchLocationByName(LocationDto location);

    ForecastDto searchForecastByLocation(LocationDto location);

}
