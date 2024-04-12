package com.weather;

import com.weather.forecast.ForecastDto;
import com.weather.forecast.service.ForecastServiceImpl;
import com.weather.location.LocationDto;
import com.weather.location.LocationServiceImpl;
import com.weather.user.User;
import com.weather.user.UserDao;
import com.weather.user.UserDaoImpl;

import java.util.List;

public class App {
    public static void main(String[] args) {

        UserDao userDao = new UserDaoImpl();
        User user = new User("A", "B");
        userDao.save(user);

        user = userDao.findByName("A").get();

        LocationServiceImpl locationService = new LocationServiceImpl();

        List<LocationDto> locations = locationService.searchLocation(LocationDto.builder().name("London").build());
        locationService.addLocationToUser(locations.get(0), user);

        locations = locationService.searchLocation(LocationDto.builder().name("Moscow").build());
        locationService.addLocationToUser(locations.get(0), user);

        locations = locationService.searchLocation(LocationDto.builder().name("Perm").build());
        locationService.addLocationToUser(locations.get(0), user);


        ForecastServiceImpl forecastService = new ForecastServiceImpl();

        List<ForecastDto> forecasts = forecastService.getForecastsForUser(user);

        for (ForecastDto f: forecasts) {
            System.out.println(f);
        }


    }
}
