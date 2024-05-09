package com.weather.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
    private static SessionFactory INSTANCE;

    private HibernateUtils() {}

    public static SessionFactory getSessionFactory() {
        if (INSTANCE == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .configure()
                    .build();

            try {
                INSTANCE = new MetadataSources(registry).buildMetadata().buildSessionFactory();

            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }

        return INSTANCE;
    }
}
