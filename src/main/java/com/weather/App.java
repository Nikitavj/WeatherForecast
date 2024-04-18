package com.weather;

import com.weather.forecast.dto.CurrentForecastDto;
import com.weather.forecast.api.ApiForecastService;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.location.LocationDto;
import com.weather.utils.HttpClientUtil;

public class App {
    public static void main(String[] args) {

//        ApiForecastService apiForecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());
//
//        LocationDto locationDto = LocationDto.builder().name("London").build();
//        locationDto = apiForecastService.searchLocationByName(locationDto).get(0);
//        CurrentForecastDto currentForecastDto = apiForecastService.searchHourlyForecastByLocation(locationDto);
//
//        System.out.println(currentForecastDto);


//        ApiForecastService apiForecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());
//
//        LocationDto locationDto = LocationDto.builder().name("London").build();
//        locationDto = apiForecastService.searchLocationByName(locationDto).get(0);
//        ForecastDto forecastDto = apiForecastService.searchCurrentForecastByLocation(locationDto);
//
//        System.out.println(forecastDto);
    }
}
