package com.weather.forecast.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class HourlyForecast {
    private Main main;
    private List<Weather> weather;
    private Wind wind;
    @JsonProperty("dt_txt")
    private String date;
}
