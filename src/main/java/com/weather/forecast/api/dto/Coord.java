package com.weather.forecast.api.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coord {
    private double lon;
    private double lat;
}
