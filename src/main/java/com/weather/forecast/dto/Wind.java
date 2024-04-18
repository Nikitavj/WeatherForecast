package com.weather.forecast.dto;

import lombok.Data;

@Data
class Wind {
    private double speed;
    private double deg;
    private double gust;
}
