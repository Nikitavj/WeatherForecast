package com.weather.forecast.service;

import com.weather.forecast.ForecastDto;
import com.weather.user.User;

import java.util.List;

interface ForecastService {

    List<ForecastDto> getForecastsForUser(User user);
}
