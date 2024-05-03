package com.weather.forecast.service;

import com.weather.forecast.location.Location;
import com.weather.forecast.location.LocationDto;

import java.util.List;

public interface ForecastService {

    List<LocationDto> getForecastsForLocations(List<Location> locations);
}
