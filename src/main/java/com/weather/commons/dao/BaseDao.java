package com.weather.commons.dao;

import com.weather.exception.DatabaseException;
import com.weather.exception.EntityDuplicationException;
import com.weather.utils.HibernateUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

@Slf4j
public abstract class BaseDao<T> implements Dao<T> {
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    @Override
    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();

            log.info("Delete: {}", entity.toString());

        } catch (HibernateException e) {
            log.warn(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    public T save(T entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();

            log.info("Save: {} ", entity);

            return entity;
        } catch (
                ConstraintViolationException e) {
            log.warn(e.getMessage());
            throw new EntityDuplicationException(
                    "This object is already contained in the database");

        } catch (HibernateException e) {
            log.warn(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
