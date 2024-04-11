package com.weather;

import com.weather.forecastapi.ApiForecastService;
import com.weather.forecastapi.ApiForecastServiceImpl;
import com.weather.forecastapi.ForecastDto;
import com.weather.location.LocationDto;

import java.util.List;

public class App {
    public static void main(String[] args) {

        ApiForecastService service = new ApiForecastServiceImpl();

        List<LocationDto> locations = service.searchLocationByCityName("London");

        LocationDto location = locations.get(0);

        ForecastDto forecast = service.searchForecastByLocation(location);

        System.out.println(forecast);
    }
}
