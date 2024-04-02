package com.weather.session.servises;

import com.weather.session.dao.SessionDao;
import com.weather.session.dao.SessionDaoImpl;
import com.weather.session.models.Session;
import com.weather.user.models.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class SessionServiceImpl implements SessionService{
    SessionDao sessionDao = new SessionDaoImpl();

    @Override
    public UUID create(User user) {
        Session session = new Session(user, LocalDateTime.now());
        return sessionDao.save(session);
    }

    @Override
    public boolean exist(UUID uuid) {

        if (sessionDao.findById(uuid).isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void remove(Session session) {
        sessionDao.delete(session);
    }
}
