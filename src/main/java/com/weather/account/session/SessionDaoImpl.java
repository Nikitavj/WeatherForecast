package com.weather.account.session;

import com.weather.commons.dao.BaseDao;
import com.weather.exception.DatabaseException;
import com.weather.exception.EntityDuplicationException;
import com.weather.utils.HibernateUtils;
import com.weather.account.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SessionDaoImpl extends BaseDao<com.weather.account.session.Session> implements SessionDao{
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    Logger log = LoggerFactory.getLogger(SessionDaoImpl.class);

    @Override
    public com.weather.account.session.Session save(com.weather.account.session.Session session) {
        try (Session sessionHiber = sessionFactory.openSession()) {
            sessionHiber.beginTransaction();
            sessionHiber.save(session);
            sessionHiber.getTransaction().commit();

            log.info("В БД сохранен объект {}", session);

            return session;

        } catch (ConstraintViolationException e) {
            log.warn("Попытка сохранить объект {}, который уже содержится в БД", session);
            throw new EntityDuplicationException(e.getMessage());

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<com.weather.account.session.Session> findById(UUID uuid) {
        try (Session sessionHiber = sessionFactory.openSession()) {
            com.weather.account.session.Session sessionWeather = sessionHiber.get(com.weather.account.session.Session.class, uuid);

            return Optional.ofNullable(sessionWeather);

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<com.weather.account.session.Session> findAll() {
        try (Session sessionHiber = sessionFactory.openSession()) {
            return sessionHiber.createQuery("from Session", com.weather.account.session.Session.class)
                    .list();

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<com.weather.account.session.Session> findByUser(User user) {
        try (Session sessionHiber = sessionFactory.openSession()) {
            com.weather.account.session.Session session = (com.weather.account.session.Session) sessionHiber
                    .createQuery("from Session where user = :user")
                    .setParameter("user", user)
                    .getSingleResultOrNull();

            return Optional.ofNullable(session);

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void delete(UUID uuid) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            com.weather.account.session.Session entity = session.get(com.weather.account.session.Session.class, uuid);
            session.remove(entity);
            session.getTransaction().commit();

            log.info("Удален объект {}", entity.toString());

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
