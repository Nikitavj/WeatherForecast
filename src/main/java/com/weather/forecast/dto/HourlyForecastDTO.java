package com.weather.forecast.dto;

import lombok.Data;

import java.util.List;

@Data
public class HourlyForecastDTO {
    private List<HourlyForecast> list;
    private City city;
}
