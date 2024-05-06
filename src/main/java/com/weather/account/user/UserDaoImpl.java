package com.weather.account.user;

import com.weather.commons.dao.BaseDao;
import com.weather.exception.DatabaseException;
import com.weather.exception.EntityDuplicationException;
import com.weather.utils.HibernateUtils;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import java.util.Optional;

@Slf4j
public class UserDaoImpl extends BaseDao<User> implements UserDao {
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    @Override
    public Optional<User> findByName(String userName) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.createQuery("from User where login = :name", User.class)
                            .setParameter("name", userName)
                    .getSingleResultOrNull();

            return Optional.ofNullable(user);

        } catch (HibernateException e) {
            log.warn(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
