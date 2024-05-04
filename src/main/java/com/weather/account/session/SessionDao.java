package com.weather.account.session;

import com.weather.commons.dao.Dao;
import com.weather.account.user.User;

import java.util.Optional;
import java.util.UUID;

public interface SessionDao extends Dao<Session> {

    Session save(Session session);

    Optional<Session> findById(UUID uuid);

    Optional<Session> findByUser(User user);

    void delete(UUID uuid);
}
