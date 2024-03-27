package com.weather.session.dao;

import com.weather.dao.BaseDao;
import com.weather.exception.DatabaseException;
import com.weather.exception.EntityDuplicationException;
import com.weather.hibernate.HibernateUtils;
import com.weather.user.models.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SessionDaoImpl extends BaseDao<com.weather.session.models.Session> implements SessionDao{
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    Logger log = LoggerFactory.getLogger(SessionDaoImpl.class);

    @Override
    public UUID save(com.weather.session.models.Session session) {
        try (Session sessionHiber = sessionFactory.openSession()) {
            sessionHiber.beginTransaction();
            UUID id = (UUID) sessionHiber.save(session);
            sessionHiber.getTransaction().commit();

            log.info("В БД сохранен объект id = {} {}", id,  session);

            return id;

        } catch (ConstraintViolationException e) {
            log.warn("Попытка сохранить объект {}, который уже содержится в БД", session);
            throw new EntityDuplicationException(e.getMessage());

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<com.weather.session.models.Session> findById(UUID uuid) {
        try (Session sessionHiber = sessionFactory.openSession()) {
            com.weather.session.models.Session sessionWeather = sessionHiber.get(com.weather.session.models.Session.class, uuid);

            return Optional.ofNullable(sessionWeather);

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<com.weather.session.models.Session> findAll() {
        try (Session sessionHiber = sessionFactory.openSession()) {
            return sessionHiber.createQuery("from Session", com.weather.session.models.Session.class)
                    .list();

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<com.weather.session.models.Session> findByUser(User user) {
        try (Session sessionHiber = sessionFactory.openSession()) {
            com.weather.session.models.Session session = (com.weather.session.models.Session) sessionHiber
                    .createQuery("from Session where user = :user")
                    .getSingleResultOrNull();

            return Optional.ofNullable(session);

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
