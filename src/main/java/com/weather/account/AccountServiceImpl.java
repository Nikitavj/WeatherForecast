package com.weather.account;

import com.weather.account.session.SessionDeletionScheduler;
import com.weather.exception.EntityDuplicationException;
import com.weather.exception.InvalidLoginException;
import com.weather.account.session.SessionDao;
import com.weather.account.session.SessionDaoImpl;
import com.weather.account.session.Session;
import com.weather.account.user.UserDao;
import com.weather.account.user.UserDaoImpl;
import com.weather.account.user.User;
import com.weather.utils.PropertiesUtil;
import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class AccountServiceImpl implements AccountService{
    private static long MAX_AGE_SESSION_SECONDS_DEFAULT = 3600;
    UserDao userDao = new UserDaoImpl();
    SessionDao sessionDao = new SessionDaoImpl();
    SessionDeletionScheduler scheduler = new SessionDeletionScheduler();

    @Override
    public UUID logup(String login, String password) {
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User(login, hashpw);
        userDao.save(user);

        Session session = new Session(
                user,
                LocalDateTime
                        .now()
                        .plusSeconds(getMaxAgeSessionSeconds()));

        sessionDao.save(session);
        scheduler.scheduleDeletion(session);

        return session.getId();
    }

    @Override
    public UUID login(String login, String password) {
        Optional<User> optUser = userDao.findByName(login);

        if (optUser.isPresent()) {
            User user = optUser.get();

            if (BCrypt.checkpw(password, user.getPassword())) {

                try {
                    Session session = new Session(
                            user,
                            LocalDateTime
                                    .now()
                                    .plusSeconds(getMaxAgeSessionSeconds()));

                    sessionDao.save(session);
                    scheduler.scheduleDeletion(session);

                    return session.getId();

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

    @Override
    public Session getSessionIfAuthenticated(UUID sessionId) {
        Optional<Session> optSession = sessionDao.findById(sessionId);

        if (optSession.isPresent()) {
            Session session = optSession.get();
            LocalDateTime expiresAt = session.getExpiresAt();

            if (expiresAt.isAfter(LocalDateTime.now())) {
                return session;
            }
            sessionDao.delete(session);
        }
        return null;
    }

    private long getMaxAgeSessionSeconds() {
        String maxAgeString = PropertiesUtil.getProperty("sessionMaxAgeSeconds");
        return maxAgeString != null ? Long.parseLong(maxAgeString) : MAX_AGE_SESSION_SECONDS_DEFAULT;
    }
}
