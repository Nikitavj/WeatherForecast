package com.weather.forecast.dto;

import lombok.Data;

import java.util.List;

@Data
class WeatherList {
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private String dt_txt;
}
