package com.weather.location;

import com.weather.commons.dao.Dao;
import com.weather.location.Location;
import com.weather.user.User;

import java.util.List;
import java.util.Optional;

public interface LocationDao extends Dao<Location> {

    int save(Location location);

    Optional<Location> findByName(String locName);

    List<Location> findAllByUser(User user);
}
