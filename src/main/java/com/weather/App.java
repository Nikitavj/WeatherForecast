package com.weather;

import com.weather.user.User;
import com.weather.user.UserDao;
import com.weather.user.UserDaoImpl;

public class App {
    public static void main(String[] args) {
        User user1 = new User("Blazer", "root1");
        User user2 = new User("Dimitro", "root2");

        UserDao dao = new UserDaoImpl();
        int id = dao.save(user1);
        int id2 = dao.save(user2);
//
//        Session session1 = new Session(user1, LocalDateTime.now());
//        Session session2 = new Session(user2, LocalDateTime.now());
//
//        SessionDaoImpl sessionDao = new SessionDaoImpl();
//        sessionDao.save(session1);
//        sessionDao.save(session2);
//
//        System.out.println(
//                sessionDao.findById(session2.getId()).get()
//        );
//
//        Location location1 = Location.builder()
//                .name("Kirov")
//                .user(user1)
//                .latitude(46789.5884)
//                .longitude(32473294.32423)
//                .build();
//
//        Location location2 = Location.builder()
//                .name("Moscov")
//                .user(user1)
//                .latitude(424589.52345)
//                .longitude(3633294.2345)
//                .build();
//
//        Location location3 = Location.builder()
//                .name("Kirov")
//                .user(user1)
//                .latitude(46784439.5884)
//                .longitude(32473294.32423)
//                .build();
//
//        LocationDaoImpl locDao = new LocationDaoImpl();
//        locDao.save(location1);
//        locDao.save(location2);
//        locDao.save(location3);
//
//        System.out.println(
//                locDao.findAll()
//        );
//
//        dao.delete(user1);
//
//        System.out.println(
//                locDao.findAll()
//        );

    }
}
