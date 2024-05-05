package com.weather.account;

import com.weather.account.session.Session;

import java.util.UUID;

public interface AccountService {

    UUID logup(String login, String password);

    UUID login(String login, String password);

    void logout(Session session);

    Session getSessionIfAuthenticated(UUID sessionId);
}
