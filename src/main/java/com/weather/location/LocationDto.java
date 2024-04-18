package com.weather.location;

import com.weather.forecast.dto.CurrentForecastDto;
import com.weather.forecast.dto.HourlyForecastDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class LocationDto {
    private int id;
    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;
    private CurrentForecastDto currentForecastDto;
    private HourlyForecastDTO hourlyForecastDTO;
}





