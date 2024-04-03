package com.weather.services;

import com.weather.exception.EntityDuplicationException;
import com.weather.exception.InvalidLoginException;
import com.weather.session.dao.SessionDao;
import com.weather.session.dao.SessionDaoImpl;
import com.weather.session.models.Session;
import com.weather.user.dao.UserDao;
import com.weather.user.dao.UserDaoImpl;
import com.weather.user.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class AccountServiceImpl implements AccountService{
    UserDao userDao = new UserDaoImpl();
    SessionDao sessionDao = new SessionDaoImpl();

    @Override
    public UUID logup(String login, String password) {
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(login, hashpw);
        userDao.save(user);

        Session session = new Session(user, LocalDateTime.now());
        return sessionDao.save(session);
    }

    @Override
    public UUID login(String login, String password) {
        Optional<User> optUser = userDao.findByName(login);

        if (optUser.isPresent()) {
            User user = optUser.get();

            if (BCrypt.checkpw(password, user.getPassword())) {

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

    @Override
    public void logout(UUID sessionId) {
        sessionDao.delete(sessionId);
    }
}
