package com.weather.forecast.dto;

import com.weather.forecast.api.dto.HourlyForecast;

public class DayForecast extends Forecast<HourlyForecast> {
    public DayForecast(int numberDay) {
        super(numberDay);
    }
}
