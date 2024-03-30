package com.weather.services;

import com.weather.session.dao.SessionDao;
import com.weather.session.dao.SessionDaoImpl;
import com.weather.session.models.Session;
import com.weather.user.dao.UserDao;
import com.weather.user.dao.UserDaoImpl;
import com.weather.user.models.User;

import java.time.LocalDateTime;
import java.util.UUID;

public class LogupServiceImpl implements LogupServise {
    UserDao userDao = new UserDaoImpl();
    SessionDao sessionDao = new SessionDaoImpl();

    @Override
    public UUID logup(String userName, String password) {

        User user = new User(userName, password);
        userDao.save(user);
        Session session = new Session(user, LocalDateTime.now());
        return sessionDao.save(session);
    }
}
