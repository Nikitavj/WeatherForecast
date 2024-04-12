package com.weather.location;

import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.user.User;
import com.weather.utils.HttpClientUtil;

import java.net.http.HttpClient;
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
    public void deleteLocationOfUser(LocationDto location, User user) {

        locationDao.deleteByLatLonForUser(user, location.getLat(), location.getLon());
    }

    @Override
    public List<LocationDto> searchLocation(LocationDto location) {

        return apiForecastService.searchLocationByName(location);
    }
}
