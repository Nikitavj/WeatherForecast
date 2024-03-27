package com.weather.user.dao;

import com.weather.dao.Dao;
import com.weather.user.models.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {

    int save(User user);

    Optional<User> findById(int id);

    Optional<User> findByName(String name);
}
