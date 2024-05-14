package com.weather.forecast;

import com.weather.forecast.api.ApiForecastService;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.forecast.dto.DayForecast;
import com.weather.forecast.dto.OverallForecast;
import com.weather.forecast.dto.MonthForecast;
import com.weather.forecast.dto.YearForecast;
import com.weather.forecast.api.dto.HourlyForecast;
import com.weather.forecast.api.dto.HourlyForecastDTO;
import com.weather.forecast.location.LocationDto;
import com.weather.utils.HttpClientUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ForecastServiceImpl implements ForecastService {
    private ApiForecastService forecastService = new ApiForecastServiceImpl(
            HttpClientUtil.getHttpClient());

    public OverallForecast getDaysFcast(LocationDto location) {
        OverallForecast forecast = new OverallForecast();
        HourlyForecastDTO dtoFcasts = forecastService.searchHourlyForecastByLocation(location);

        Map<Integer, Map<Integer, Map<Integer, List<HourlyForecast>>>> fcastsMap = dtoFcasts.getList()
                .stream()
                .sorted(Comparator.comparing(HourlyForecast::getDate))
                .collect(Collectors.groupingBy(h -> h.getDate().getYear(),
                        Collectors.groupingBy(h -> h.getDate().getMonthValue(),
                                Collectors.groupingBy(h -> h.getDate().getDayOfMonth()))));


        for (Map.Entry<Integer, Map<Integer, Map<Integer, List<HourlyForecast>>>> entry: fcastsMap.entrySet()) {

            YearForecast yearForecast = new YearForecast(entry.getKey());
            forecast.addForecast(yearForecast);

            for (Map.Entry<Integer, Map<Integer, List<HourlyForecast>>> month :entry.getValue().entrySet()) {

                MonthForecast monthForecast = new MonthForecast(month.getKey());
                yearForecast.addForecast(monthForecast);
                List<DayForecast> list = new ArrayList<>();

                for (Map.Entry<Integer, List<HourlyForecast>> day:month.getValue().entrySet()) {

                    DayForecast dayForecast = new DayForecast(day.getKey());
                    dayForecast.setForecasts(day.getValue());
                    list.add(dayForecast);
                }

                monthForecast.setForecasts(list.stream()
                                .sorted(Comparator.comparing(DayForecast::getNumber))
                                .toList());
            }
        }

        return forecast;
    }
}
