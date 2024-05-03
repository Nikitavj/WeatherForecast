package com.weather.account.session;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SessionDeletionScheduler {
    private ScheduledExecutorService schedule = new ScheduledThreadPoolExecutor(1);
    private SessionDao dao = new SessionDaoImpl();

    public void scheduleDeletion(Session session) {
        Runnable task = () -> {
            dao.delete(session);
        };

        LocalDateTime expiresAt = session.getExpiresAt();
        Duration duration = Duration.between(LocalDateTime.now(), expiresAt);
        schedule.schedule(task, duration.getSeconds(), TimeUnit.SECONDS);
    }
}
