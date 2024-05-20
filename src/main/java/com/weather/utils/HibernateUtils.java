package com.weather.utils;

import com.weather.exception.DatabaseException;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static SessionFactory INSTANCE;

    private HibernateUtils() {
    }

    public static SessionFactory getSessionFactory() {
        if (INSTANCE == null) {
            Configuration cfg = new Configuration();
            cfg.configure();
            cfg.setProperty("hibernate.connection.username", System.getenv("JDBC_USER"));
            cfg.setProperty("hibernate.connection.password", System.getenv("JDBC_PASSWORD"));
            cfg.setProperty("hibernate.connection.url", System.getenv("JDBC_URL"));

            try {
                INSTANCE = cfg.buildSessionFactory();

            } catch (HibernateException e) {
                throw new DatabaseException(e.getMessage());
            }
        }

        return INSTANCE;
    }
}
