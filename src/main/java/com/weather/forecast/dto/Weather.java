package com.weather.forecast.dto;

import lombok.Data;

@Data
class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}
