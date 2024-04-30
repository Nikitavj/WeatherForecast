package com.weather;

import com.weather.forecast.api.ApiForecastService;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.forecast.dto.CurrentForecastDto;
import com.weather.forecast.dto.HourlyForecastDTO;
import com.weather.location.LocationDto;
import com.weather.utils.HttpClientUtil;

import java.util.List;

public class App {

    public static void main(String[] args) {

        ApiForecastService apiForecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());
        LocationDto location = LocationDto.builder().lat(55.7504461).lon(37.6174943).build();
        HourlyForecastDTO hourlyForecastDTO = apiForecastService.searchHourlyForecastByLocation(location);

        System.out.println(hourlyForecastDTO);

    }
}
