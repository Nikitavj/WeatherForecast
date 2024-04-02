package com.weather.services;

import com.weather.exception.EntityDuplicationException;
import com.weather.exception.InvalidLoginException;
import com.weather.session.dao.SessionDao;
import com.weather.session.dao.SessionDaoImpl;
import com.weather.session.models.Session;
import com.weather.user.dao.UserDao;
import com.weather.user.dao.UserDaoImpl;
import com.weather.user.models.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class LoginServiceImpl implements LoginService {
    UserDao userDao = new UserDaoImpl();
    SessionDao sessionDao = new SessionDaoImpl();

    @Override
    public UUID login(String userName, String password) {
        Optional<User> optUser = userDao.findByName(userName);

        if (optUser.isPresent()) {
            User user = optUser.get();

            if (user.getPassword().equals(password)) {

                try {
                    return sessionDao.save(new Session(
                            user,
                            LocalDateTime.now()));

                } catch (EntityDuplicationException e) {
                    return sessionDao.findByUser(user).get().getId();
                }

            } else {
                throw new InvalidLoginException("Пароль неверный");
            }

        } else {
            throw new InvalidLoginException("Пользователь с данным именем не зарегистрирован");
        }
    }
}
