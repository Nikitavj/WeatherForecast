package com.weather.forecast.dto;

import lombok.Data;

@Data
class City {
    private int id;
    private String name;
    private Coord coord;
}
