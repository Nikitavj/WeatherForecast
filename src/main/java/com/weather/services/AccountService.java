package com.weather.services;

import java.util.UUID;

public interface AccountService {

    UUID logup(String login, String password);

    UUID login(String login, String password);

    void logout(UUID sessionId);
}
