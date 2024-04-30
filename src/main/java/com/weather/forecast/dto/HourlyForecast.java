package com.weather.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class HourlyForecast {
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    private String dt_txt;
}
