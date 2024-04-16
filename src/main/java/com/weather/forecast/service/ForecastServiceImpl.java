package com.weather.forecast.service;

import com.weather.forecast.ForecastDto;
import com.weather.forecast.api.ApiForecastService;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.location.Location;
import com.weather.location.LocationDao;
import com.weather.location.LocationDaoImpl;
import com.weather.location.LocationDto;
import com.weather.user.User;
import com.weather.utils.HttpClientUtil;

import java.util.ArrayList;
import java.util.List;

public class ForecastServiceImpl implements ForecastService {
    LocationDao locationDao = new LocationDaoImpl();
    ApiForecastService apiForecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());

    @Override
    public List<ForecastDto> getForecastsForUser(User user) {
        List<ForecastDto> forecasts = new ArrayList<>();

        List<Location> locations = locationDao.findAllByUser(user);

        int numberInList = 0;
        for (Location l: locations) {
            ForecastDto forecast = apiForecastService.searchCurrentForecastByLocation(LocationDto
                    .builder()
                    .lat(l.getLatitude())
                    .lon(l.getLongitude())
                    .build());

            if (forecasts != null) {
                forecast.setId(numberInList++);
                forecasts.add(forecast);
            }
        }

        return forecasts;
    }
}
