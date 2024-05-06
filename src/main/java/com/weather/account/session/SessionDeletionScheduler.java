package com.weather.account.session;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SessionDeletionScheduler {
    private static SessionDeletionScheduler INSTANCE;
    private SessionDao dao = new SessionDaoImpl();
    private ScheduledExecutorService schedule = new ScheduledThreadPoolExecutor(1);

    private SessionDeletionScheduler() {};

    public static SessionDeletionScheduler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SessionDeletionScheduler();
        }
        return INSTANCE;
    }

    public void scheduleDeletion(Session session) {
        Runnable task = () -> {
            dao.delete(session);
        };

        LocalDateTime expiresAt = session.getExpiresAt();
        Duration duration = Duration.between(LocalDateTime.now(), expiresAt);
        schedule.schedule(task, duration.getSeconds(), TimeUnit.SECONDS);
    }
}
