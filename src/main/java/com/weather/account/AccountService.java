package com.weather.account;

import com.weather.session.Session;

import java.util.UUID;

public interface AccountService {

    UUID logup(String login, String password);

    UUID login(String login, String password);

    void logout(UUID sessionId);

    Session getSessionIfAuthenticated(UUID sessionId);
}
