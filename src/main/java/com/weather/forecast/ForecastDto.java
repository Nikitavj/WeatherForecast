package com.weather.forecast;

import lombok.Data;

import java.util.List;

@Data
public class ForecastDto {
    private int id;
    private String name;
    private Coord coord;
    private List<Weather> weather;
    private Main main;
    private Wind wind;
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

@Data
class Wind {
    private double speed;
    private double deg;
    private double gust;
}
