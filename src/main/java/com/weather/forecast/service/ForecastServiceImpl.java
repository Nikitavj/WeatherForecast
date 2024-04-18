package com.weather.forecast.service;

import com.weather.forecast.api.ApiForecastService;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.location.Location;
import com.weather.location.LocationDao;
import com.weather.location.LocationDaoImpl;
import com.weather.location.LocationDto;
import com.weather.utils.HttpClientUtil;

import java.util.List;

public class ForecastServiceImpl implements ForecastService {
    LocationDao locationDao = new LocationDaoImpl();
    ApiForecastService apiForecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());

//    @Override
//    public ForecastDto getForecastForLocation(Location location) {
//
////        int count = 0;
////
////        for (Location l: locations) {
////            ForecastDto forecast = apiForecastService.searchCurrentForecastByLocation(LocationDto
////                    .builder()
////                    .lat(l.getLatitude())
////                    .lon(l.getLongitude())
////                    .build());
////
////            if (forecast != null) {
////                l.setId(count++);
////            }
////        }
//
//
//
//        return apiForecastService.searchCurrentForecastByLocation(LocationDto
//                    .builder()
//                    .lat(location.getLatitude())
//                    .lon(location.getLongitude())
//                    .build());;
//    }

    @Override
    public List<LocationDto> getForecastsForLocations(List<Location> locations) {
        return null;
    }
}
