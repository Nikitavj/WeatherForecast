package com.weather.account.session;

import com.weather.commons.dao.BaseDao;
import com.weather.exception.DatabaseException;
import com.weather.exception.EntityDuplicationException;
import com.weather.utils.HibernateUtils;
import com.weather.account.user.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class SessionDaoImpl extends BaseDao<com.weather.account.session.Session> implements SessionDao {
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    Logger log = LoggerFactory.getLogger(SessionDaoImpl.class);

    @Override
    public Session save(Session sessionUser) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(sessionUser);
            session.getTransaction().commit();

            log.info("В БД сохранен объект {}", sessionUser);

            return sessionUser;

        } catch (ConstraintViolationException e) {
            log.warn("Попытка сохранить объект {}, который уже содержится в БД", sessionUser);
            throw new EntityDuplicationException(e.getMessage());

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<com.weather.account.session.Session> findById(UUID uuid) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            Session sessionUser = session.get(Session.class, uuid);

            return Optional.ofNullable(sessionUser);

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<com.weather.account.session.Session> findByUser(User user) {
        try (org.hibernate.Session session = sessionFactory.openSession()) {
            Session sessionUser = (Session) session
                    .createQuery("from Session where user = :user")
                    .setParameter("user", user)
                    .getSingleResultOrNull();

            return Optional.ofNullable(sessionUser);

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
