package com.weather.account.user;

import com.weather.commons.dao.Dao;
import java.util.Optional;

public interface UserDao extends Dao<User> {
    User save(User user);

    Optional<User> findByName(String name);
}
