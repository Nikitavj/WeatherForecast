package com.weather.forecast.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Forecast<T> {
    private int number;
    private List<T> forecasts = new ArrayList<>();

    public Forecast() {
    }

    public void addForecast(T forecast) {
        forecasts.add(forecast);
    }

    public Forecast(int number) {
        this.number = number;
    }
}
