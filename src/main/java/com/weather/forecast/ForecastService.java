package com.weather.forecast;

import com.weather.forecast.dto.GeneralForecast;
import com.weather.forecast.location.LocationDto;

public interface ForecastService {
    public GeneralForecast getDaysFcast(LocationDto location);
}
