package com.weather.session.dao;

import com.weather.dao.Dao;
import com.weather.session.Session;
import com.weather.user.User;

import java.util.Optional;
import java.util.UUID;

public interface SessionDao extends Dao<Session> {
    UUID save(Session session);

    Optional<Session> findByUser(User user);
}
