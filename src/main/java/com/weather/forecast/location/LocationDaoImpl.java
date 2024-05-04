package com.weather.forecast.location;

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

public class LocationDaoImpl extends BaseDao<Location> implements LocationDao {
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
    Logger log = LoggerFactory.getLogger(LocationDaoImpl.class);
    @Override
    public List<Location> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Location", Location.class).list();

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Location> findByName(String locName) {
        try (Session session = sessionFactory.openSession()) {
            Location location = (Location) session.createQuery("from Location where name = :locName")
                    .setParameter("locName", locName)
                    .getSingleResultOrNull();

            return Optional.ofNullable(location);

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List findAllByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Location where user = :user order by id")
                    .setParameter("user", user)
                    .getResultList();

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Location getByNumber(int number, User user) {
        try(Session session = sessionFactory.openSession()) {
            return (Location) session.createQuery("from Location where user = :user")
                    .setParameter("user", user)
                    .setFirstResult(number)
                    .setMaxResults(1)
                    .getSingleResult();

        } catch (HibernateException e) {
            log.warn("Исключение БД: {}", e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
