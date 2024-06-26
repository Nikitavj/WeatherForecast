package com.weather.forecast.api;

import com.weather.forecast.api.dto.CurrentForecastDto;
import com.weather.forecast.api.dto.HourlyForecastDTO;
import com.weather.forecast.location.LocationDto;
import java.util.List;

public interface ApiForecastService {

    List<LocationDto> searchLocationByName(LocationDto location);

    CurrentForecastDto searchCurrentForecastByLocation(LocationDto location);

    HourlyForecastDTO searchHourlyForecastByLocation(LocationDto location);

}
