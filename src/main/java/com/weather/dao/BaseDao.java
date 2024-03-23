package com.weather.dao;

import com.weather.exception.DatabaseException;
import com.weather.exception.EntityDuplicationException;
import com.weather.hibernate.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDao<T> implements Dao<T> {
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    private Logger log = LoggerFactory.getLogger(BaseDao.class);

    @Override
    public void save(T entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();

            log.info("В БД сохранен объект {}", entity.toString());

        } catch (ConstraintViolationException e) {
            log.warn("Попытка сохранить объект {}, который содержится в БД", entity.toString());
            throw new EntityDuplicationException(e.getMessage());

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public void update(T entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.merge(entity);
            session.getTransaction().commit();

            log.info("В БД обновлен объект {}", entity.toString());

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

    }

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();

            log.info("Удален объект {}", entity.toString());

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}