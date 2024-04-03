package com.weather.services;

import com.weather.session.dao.SessionDao;
import com.weather.session.dao.SessionDaoImpl;
import com.weather.session.models.Session;

import java.util.Optional;
import java.util.UUID;

public class SessionServiceImpl implements  SessionService{
    SessionDao sessionDao = new SessionDaoImpl();

    @Override
    public Session getSessionById(UUID sessionId) {

        Optional<Session> optSession = sessionDao.findById(sessionId);
        if (optSession.isPresent()) {
            return optSession.get();
        } else {
            return null;
        }
    }
}
