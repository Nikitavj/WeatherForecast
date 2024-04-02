package com.weather.session.servises;

import com.weather.session.models.Session;
import com.weather.user.models.User;

import java.util.UUID;

public interface SessionService {

    UUID create(User user);

    boolean exist(UUID uuid);

    void remove(Session session);
}
