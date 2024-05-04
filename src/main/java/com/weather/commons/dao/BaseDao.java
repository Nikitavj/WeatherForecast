package com.weather.commons.dao;

import com.weather.exception.DatabaseException;
import com.weather.exception.EntityDuplicationException;
import com.weather.utils.HibernateUtils;
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
    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();

            log.info("Удален объект {}", entity.toString());

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public T save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();

            log.info("В БД сохранен объект {} ", entity);

            return entity;
        } catch (
                ConstraintViolationException e) {
            log.warn("Попытка сохранить объект {}, который уже содержится в БД", entity);
            throw new EntityDuplicationException("Локация была добавлена ранее");

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
