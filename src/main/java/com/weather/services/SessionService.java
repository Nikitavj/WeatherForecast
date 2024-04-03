package com.weather.services;

import com.weather.session.models.Session;

import java.util.UUID;

public interface SessionService {

    Session getSessionById(UUID sessionId);
}
