package com.weather.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class HourlyForecastDTO {
    private List<HourlyForecast> list;
    private City city;
}
