package com.weather.forecast;

import lombok.Data;

import java.util.List;

@Data
public class ForecastDto {
    private Coord coord;
    private List<Weather> weather;
    private Main main;
}

@Data
class Coord {
    private double lon;
    private double lat;
}

@Data
class Weather {
    private int id;
    private String main;
    private String description;
    private String icon;
}

@Data
class Main {
    private double temp;
    private double pressure;
    private double humidity;
}
