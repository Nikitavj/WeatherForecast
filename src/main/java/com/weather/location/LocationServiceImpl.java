package com.weather.location;

import com.weather.forecast.dto.CurrentForecastDto;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.user.User;
import com.weather.utils.HttpClientUtil;

import java.util.ArrayList;
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
        Location location = list.get(locationDto.getId());
        locationDao.delete(location);
    }

    @Override
    public List<LocationDto> getUsersLocations(User user) {
        List<LocationDto> locationDtoList = new ArrayList<>();

        List<Location> locations = locationDao.findAllByUser(user);

        int count = 0;

        for (Location l: locations) {
            LocationDto locationDto = LocationDto
                    .builder()
                    .lat(l.getLatitude())
                    .lon(l.getLongitude())
                    .build();

            CurrentForecastDto forecast = apiForecastService.searchCurrentForecastByLocation(locationDto);

            if (forecast != null) {
                locationDto.setCurrentForecastDto(forecast);
                locationDto.setId(count++);
                locationDtoList.add(locationDto);
            }
        }
        return locationDtoList;
    }

    public LocationDto getLocationByIdForUser(int idLocation, User user) {

        Location location = locationDao.getByNumber(idLocation, user);
        return LocationDto
                .builder()
                .lat(location.getLatitude())
                .lon(location.getLongitude())
                .build();
    }
}
