package com.weather.location;

import com.weather.forecastapi.ApiForecastServiceImpl;
import com.weather.user.User;

import java.net.http.HttpClient;
import java.util.List;

public class LocationServiceImpl implements LocationService {
    private HttpClient httpClient = HttpClient.newBuilder().build();
    private ApiForecastServiceImpl forecastService = new ApiForecastServiceImpl(httpClient);
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

        return forecastService.searchLocationByName(location);
    }
}
