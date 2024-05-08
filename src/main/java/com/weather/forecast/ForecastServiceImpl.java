package com.weather.forecast;

import com.weather.forecast.api.ApiForecastService;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.forecast.dto.DayForecast;
import com.weather.forecast.dto.GeneralForecast;
import com.weather.forecast.dto.MonthForecast;
import com.weather.forecast.dto.YearForecast;
import com.weather.forecast.api.dto.HourlyForecast;
import com.weather.forecast.api.dto.HourlyForecastDTO;
import com.weather.forecast.location.LocationDto;
import com.weather.utils.HttpClientUtil;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ForecastServiceImpl implements ForecastService {
    private ApiForecastService forecastService = new ApiForecastServiceImpl(HttpClientUtil.getHttpClient());

    public GeneralForecast getDaysFcast(LocationDto location) {
        GeneralForecast forecast = new GeneralForecast();
        HourlyForecastDTO dto = forecastService.searchHourlyForecastByLocation(location);

        Map<Integer, Map<Integer, Map<Integer,List<HourlyForecast>>>> fmap = dto.getList().stream()
                .collect(Collectors.groupingBy(h -> h.getDate().getYear(),
                        Collectors.groupingBy(h -> h.getDate().getMonthValue(),
                                Collectors.groupingBy(h -> h.getDate().getDayOfMonth()))));

        for (Map.Entry<Integer, Map<Integer, Map<Integer,List<HourlyForecast>>>> entry: fmap.entrySet()) {

            YearForecast yearForecast = new YearForecast(entry.getKey());
            forecast.addForecast(yearForecast);

            for (Map.Entry<Integer, Map<Integer,List<HourlyForecast>>> fmonth :entry.getValue().entrySet()) {

                MonthForecast monthForecast = new MonthForecast(fmonth.getKey());
                yearForecast.addForecast(monthForecast);

                for (Map.Entry<Integer,List<HourlyForecast>> fday:fmonth.getValue().entrySet()) {

                    DayForecast dayForecast = new DayForecast(fday.getKey());
                    dayForecast.setForecasts(fday.getValue());
                    monthForecast.addForecast(dayForecast);
                }
            }
        }

        return forecast;
    }
}
