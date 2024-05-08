package com.weather.forecast.dto;

public class MonthForecast extends Forecast<DayForecast> {
    public MonthForecast(int numberMonth) {
        super(numberMonth);
    }
}
