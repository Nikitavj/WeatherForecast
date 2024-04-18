package com.weather.forecast.dto;

import lombok.Data;

import java.util.List;

@Data
public class CurrentForecastDto {
    private String name;
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
}











