package com.weather.forecast.location;

import com.weather.commons.dao.BaseDao;
import com.weather.exception.DatabaseException;
import com.weather.utils.HibernateUtils;
import com.weather.account.user.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;

@Slf4j
public class LocationDaoImpl extends BaseDao<Location> implements LocationDao {
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    @Override
    public List findAllByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Location where user = :user order by id")
                    .setParameter("user", user)
                    .getResultList();

        } catch (HibernateException e) {
            log.warn(e.getMessage());
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
            log.warn(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
