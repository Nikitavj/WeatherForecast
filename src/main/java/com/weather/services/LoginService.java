package com.weather.services;

import java.util.UUID;

public interface LoginService {

    UUID login(String userName, String password);
}
