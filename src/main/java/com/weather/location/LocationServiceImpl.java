package com.weather.location;

import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.user.User;
import com.weather.utils.HttpClientUtil;

import java.util.List;

public class LocationServiceImpl implements LocationService {
    private ApiForecastServiceImpl apiForecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());
    private LocationDao locationDao = new LocationDaoImpl();

    @Override
    public void addLocationToUser(LocationDto locationDto, User user) {
        Location location = Location.builder()
                .longitude(locationDto.getLon())
                .latitude(locationDto.getLat())
                .name(locationDto.getName())
                .user(user)
                .build();
        locationDao.save(location);
    }

    @Override
    public void deleteLocationOfUser(LocationDto locationDto, User user) {

        List<Location> list = locationDao.findAllByUser(user);
        Location location = list.get(locationDto.getNumberLocForUser());
        locationDao.delete(location);
    }

    @Override
    public List<LocationDto> searchLocation(LocationDto location) {

        return apiForecastService.searchLocationByName(location);
    }
}
