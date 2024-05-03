package com.weather.account.user;

import com.weather.commons.dao.BaseDao;
import com.weather.exception.DatabaseException;
import com.weather.exception.EntityDuplicationException;
import com.weather.utils.HibernateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    Logger log = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public int save(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            int id = (int) session.save(user);
            session.getTransaction().commit();

            log.info("В БД сохранен объект id = {} {}", id,  user);

            return id;

        } catch (ConstraintViolationException e) {
            log.warn("Попытка сохранить объект {}, который содержится в БД", user);
            throw new EntityDuplicationException("Пользователь с таким именем уже существует");

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<User> findByName(String userName) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery("from User where login = :name", User.class)
                            .setParameter("name", userName)
                    .getSingleResultOrNull();

            return Optional.ofNullable(user);

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
