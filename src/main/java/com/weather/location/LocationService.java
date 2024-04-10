package com.weather.location;

import com.weather.user.User;

interface LocationService {

    void addLocationToUser (Location location, User user);
    
    void deleteLocationOfUser (Location location, User user);

}
