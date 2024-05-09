package com.weather.forecast;

import com.weather.forecast.dto.OverallForecast;
import com.weather.forecast.location.LocationDto;

public interface ForecastService {
    public OverallForecast getDaysFcast(LocationDto location);
}
