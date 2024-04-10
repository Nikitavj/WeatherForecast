package com.weather.forecastapi;

import com.weather.location.Location;

import java.util.List;

public interface ApiForecastService {

    List<Location> searchLocationByCityName(String name);

    Forecast searchForecastByLocation(Location location);

}
