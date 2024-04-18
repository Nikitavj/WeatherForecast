package com.weather.forecast.service;

import com.weather.location.Location;
import com.weather.location.LocationDto;

import java.util.List;

public interface ForecastService {

    List<LocationDto> getForecastsForLocations(List<Location> locations);
}
