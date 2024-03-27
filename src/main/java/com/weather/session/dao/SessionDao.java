package com.weather.session.dao;

import com.weather.dao.Dao;
import com.weather.session.models.Session;
import com.weather.user.models.User;

import java.util.Optional;
import java.util.UUID;

public interface SessionDao extends Dao<Session> {

    UUID save(Session session);

    Optional<Session> findById(UUID uuid);

    Optional<Session> findByUser(User user);
}
