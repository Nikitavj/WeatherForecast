package com.weather.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
    private static SessionFactory sessionfactory;

    static {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();

        try {
            sessionfactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionfactory;
    }
}
