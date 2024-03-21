package com.weather;

import com.weather.hibernate.HibernateUtils;
import com.weather.user.User;
import org.hibernate.Session;

public class App {
    public static void main(String[] args) {
        User user = new User("Blazer", "root");

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
    }
}
