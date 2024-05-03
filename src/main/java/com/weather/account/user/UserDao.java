package com.weather.account.user;

import com.weather.commons.dao.Dao;

import java.util.Optional;

public interface UserDao extends Dao<User> {

    int save(User user);

    Optional<User> findById(int id);

    Optional<User> findByName(String name);
}
