package com.weather.user.dao;

import com.weather.dao.BaseDao;
import com.weather.exception.DatabaseException;
import com.weather.hibernate.HibernateUtils;
import com.weather.user.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public Optional<User> getById(int id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            Optional.ofNullable(session.get(User.class, id));

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            return session.createQuery("from User", User.class).list();

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByName(String name) {
        try (Session session = sessionFactory.getCurrentSession()) {
            return Optional.ofNullable(
                    session.createQuery("from User where login = :name", User.class)
                            .getResultList()
                            .get(0));
        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
