package com.weather.forecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main {
    private double temp;
    private double pressure;
    private double humidity;
}
