package com.weather;

import com.weather.forecast.ForecastDto;
import com.weather.forecast.api.ApiForecastService;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.forecast.service.ForecastServiceImpl;
import com.weather.location.LocationDto;
import com.weather.location.LocationServiceImpl;
import com.weather.user.User;
import com.weather.user.UserDao;
import com.weather.user.UserDaoImpl;
import com.weather.utils.HttpClientUtil;

import java.util.List;

public class App {
    public static void main(String[] args) {

        ApiForecastService apiForecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());

        LocationDto locationDto = LocationDto.builder().name("London").build();
        locationDto = apiForecastService.searchLocationByName(locationDto).get(0);
        ForecastDto forecastDto = apiForecastService.searchHourlyForecastByLocation(locationDto);

        System.out.println(forecastDto);


//        ApiForecastService apiForecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());
//
//        LocationDto locationDto = LocationDto.builder().name("London").build();
//        locationDto = apiForecastService.searchLocationByName(locationDto).get(0);
//        ForecastDto forecastDto = apiForecastService.searchCurrentForecastByLocation(locationDto);
//
//        System.out.println(forecastDto);
    }
}
