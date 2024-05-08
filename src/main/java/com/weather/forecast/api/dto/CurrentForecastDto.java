package com.weather.forecast.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class CurrentForecastDto {
    private String name;
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
}











