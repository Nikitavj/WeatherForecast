package com.weather.forecast.location;

import com.weather.commons.dao.Dao;
import com.weather.account.user.User;

import java.util.List;
import java.util.Optional;

public interface LocationDao extends Dao<Location> {

    List<Location> findAllByUser(User user);

    Location getByNumber(int number, User user);
}
