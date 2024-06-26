package com.weather.account.session;

import com.weather.commons.dao.BaseDao;
import com.weather.exception.DatabaseException;
import com.weather.utils.HibernateUtils;
import com.weather.account.user.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class SessionDaoImpl extends BaseDao<com.weather.account.session.Session> implements SessionDao {
    private SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

    @Override
    public Optional<Session> findById(UUID uuid) {
        try (var session = sessionFactory.openSession()) {
            Session sessionUser = session.get(Session.class, uuid);
            return Optional.ofNullable(sessionUser);

        } catch (HibernateException e) {
            log.warn(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Session> findByUser(User user) {
        try (var session = sessionFactory.openSession()) {
            Session sessionUser = (Session) session
                    .createQuery("from Session where user = :user")
                    .setParameter("user", user)
                    .getSingleResultOrNull();

            return Optional.ofNullable(sessionUser);

        } catch (HibernateException e) {
            log.warn(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
