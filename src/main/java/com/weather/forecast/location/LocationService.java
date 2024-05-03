package com.weather.forecast.location;

import com.weather.account.user.User;

import java.util.List;

public interface LocationService {

    void addLocationToUser (LocationDto location, User user);
    
    void deleteLocationOfUser (LocationDto location, User user);

//    List<LocationDto> searchLocationByName(LocationDto location);

    List<LocationDto> getUsersLocations (User user);

    LocationDto getLocationByIdForUser(int idLocation, User user);
}
