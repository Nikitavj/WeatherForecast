package com.weather.user.dao;

import com.weather.dao.Dao;
import com.weather.user.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {
    Optional<User> findByName(String name);
}
