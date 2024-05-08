package com.weather.forecast.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HourlyForecast {
    private Main main;
    private List<Weather> weather;
    private Wind wind;

    @JsonProperty("dt_txt")
    @JsonFormat
            (pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime date;
}
