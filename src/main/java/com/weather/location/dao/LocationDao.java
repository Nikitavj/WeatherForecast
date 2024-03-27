package com.weather.location.dao;

import com.weather.dao.Dao;
import com.weather.location.models.Location;
import com.weather.user.models.User;

import java.util.List;
import java.util.Optional;

public interface LocationDao extends Dao<Location> {

    int save(Location location);

    Optional<Location> findByName(String locName);

    List<Location> findAllByUser(User user);
}
