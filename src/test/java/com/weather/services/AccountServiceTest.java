package com.weather.services;

import com.weather.exception.EntityDuplicationException;
import com.weather.exception.InvalidLoginException;
import com.weather.session.dao.SessionDao;
import com.weather.session.dao.SessionDaoImpl;
import com.weather.session.models.Session;
import com.weather.user.dao.UserDao;
import com.weather.user.dao.UserDaoImpl;
import com.weather.user.models.User;
import com.weather.utils.HibernateUtils;
import com.weather.utils.PropertiesUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {

    private static AccountService accountService;
    private static UserDao userDao;
    private static SessionDao sessionDao;
    private static final String login = "user@gmail.com";
    private static final String password = "Password1";

    @BeforeAll
    static void beforeAll() {
        accountService = new AccountServiceImpl();
        userDao = new UserDaoImpl();
        sessionDao = new SessionDaoImpl();
    }

    @BeforeEach
    void setUp() {
        try (org.hibernate.Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();

            session.createNativeQuery("""
                    delete from locations;
                    delete from sessions;
                    delete from users;
                    """).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Test
    void logupWithCheckingUserInDB() {

        Optional<User> userOpt = userDao.findByName(login);
        assertFalse(userOpt.isPresent());

        accountService.logup(login, password);
        userOpt = userDao.findByName(login);
        assertTrue(userOpt.isPresent());
        assertTrue(login.equals(userOpt.get().getLogin()));
    }

    @Test
    void logupWithCheckingUniqueUserInDB() {

        accountService.logup(login, password);
        Optional<User> userOpt = userDao.findByName(login);
        assertTrue(userOpt.isPresent());

        assertThrows(EntityDuplicationException.class,
                () -> accountService.logup(login, password));
    }

    @Test
    void logupWithCheckingSessionInDB() {

        UUID uuid = accountService.logup(login, password);
        Optional<Session> sessionOpt = sessionDao.findById(uuid);

        assertTrue(sessionOpt.isPresent());
        assertTrue(uuid.equals(sessionOpt.get().getId()));
    }

    @Test
    void loginWithCheckingCreationSessionInDB() {

        UUID uuid = accountService.logup(login, password);
        Optional<Session> sessionOpt = sessionDao.findById(uuid);
        assertTrue(sessionOpt.isPresent());

        accountService.logout(uuid);
        sessionOpt = sessionDao.findById(uuid);
        assertFalse(sessionOpt.isPresent());

        uuid = accountService.login(login, password);
        sessionOpt = sessionDao.findById(uuid);
        assertTrue(sessionOpt.isPresent());
    }

    @Test
    void loginWithCheckingNameUser() {
        Optional<User> userOpt = userDao.findByName(login);
        assertTrue(userOpt.isEmpty());

        assertThrows(InvalidLoginException.class,
                () -> accountService.login(login, password));
    }

    @Test
    void loginWithCheckingPasswordUser() {
        accountService.logup(login, password);
        Optional<User> userOpt = userDao.findByName(login);
        assertTrue(userOpt.isPresent());

        String wrongPassword = "fdsjl1";
        assertFalse(wrongPassword.equals(password));
        assertThrows(InvalidLoginException.class,
                () -> accountService.login(login, wrongPassword));
    }

    @Test
    void logoutWithCheckingSessionInDB() {
        UUID uuid = accountService.logup(login, password);
        Optional<Session> sessionOpt = sessionDao.findById(uuid);
        assertTrue(sessionOpt.isPresent());

        accountService.logout(uuid);

        sessionOpt = sessionDao.findById(uuid);
        assertFalse(sessionOpt.isPresent());
    }

    @Test
    void checkAuthenticationAfterLogout() {
        UUID uuid = accountService.logup(login, password);
        Optional<Session> sessionOpt = sessionDao.findById(uuid);
        assertTrue(sessionOpt.isPresent());

        assertTrue(accountService.getSessionIfAuthenticated(uuid) != null);

        accountService.logout(uuid);
        sessionOpt = sessionDao.findById(uuid);
        assertFalse(sessionOpt.isPresent());

        assertFalse(accountService.getSessionIfAuthenticated(uuid) != null);
    }

    @Test
    void checkAuthenticationAfterExpiredLifetimeSession() {
        UUID uuid = accountService.logup(login, password);
        Optional<Session> sessionOpt = sessionDao.findById(uuid);
        assertTrue(sessionOpt.isPresent());

        assertTrue(accountService.getSessionIfAuthenticated(uuid) != null);

        long lifetimeSession = Long.parseLong(PropertiesUtil.getProperty("sessionMaxAgeSeconds"));

        try {
            Thread.sleep(lifetimeSession * 1500);

            assertFalse(accountService.getSessionIfAuthenticated(uuid) != null);
            sessionOpt = sessionDao.findById(uuid);
            assertFalse(sessionOpt.isPresent());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}