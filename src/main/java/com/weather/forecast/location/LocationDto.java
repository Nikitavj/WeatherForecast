package com.weather.forecast.location;

import com.weather.forecast.api.dto.CurrentForecastDto;
import com.weather.forecast.api.dto.HourlyForecastDTO;
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





