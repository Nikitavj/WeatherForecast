package com.weather;

import com.weather.forecastapi.ApiForecastService;
import com.weather.forecastapi.ApiForecastServiceImpl;
import com.weather.forecastapi.ForecastDto;
import com.weather.location.LocationDto;
import com.weather.location.LocationServiceImpl;
import com.weather.user.User;
import com.weather.user.UserDao;
import com.weather.user.UserDaoImpl;

import java.net.http.HttpClient;
import java.util.List;

public class App {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoImpl();
        User user = new User("A", "B");
        userDao.save(user);

        LocationServiceImpl locationService = new LocationServiceImpl();

        List<LocationDto> locations = locationService.searchLocation(LocationDto.builder().name("London").build());

        locationService.addLocationToUser(locations.get(0), userDao.findByName("A").get());

        System.out.println(locations);

        locationService.deleteLocationOfUser(locations.get(0), userDao.findByName("A").get());

    }
}
