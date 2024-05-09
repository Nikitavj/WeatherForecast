package com.weather.services;

import com.weather.exception.ApiWeatherException;
import com.weather.exception.ApiWeatherNotFoundException;
import com.weather.forecast.api.ApiForecastService;
import com.weather.forecast.api.ApiForecastServiceImpl;
import com.weather.forecast.api.dto.*;
import com.weather.forecast.location.LocationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ApiForecastServiceTest {
    @Mock
    private static HttpClient httpClient;
    @Mock
    private static HttpResponse<String> response;
    private static ApiForecastService apiForecastService;
    final double LATITUDE = 55.7504461;
    final double LONGITUDE = 37.6174943;

    @BeforeEach
    void init() throws IOException, InterruptedException {
        apiForecastService = new ApiForecastServiceImpl(httpClient);
        Mockito.when(httpClient.send(Mockito.any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(response);
        Mockito.when(response.statusCode()).thenReturn(200);
    }

    @Test
    public void searchLocationByName() {

        final String JSON_LOCATIONS = """
                [
                    {
                        "name": "Moscow",
                        "lat": 55.7504461,
                        "lon": 37.6174943,
                        "country": "RU",
                        "state": "Moscow"
                    },
                    {
                        "name": "Moscow",
                        "local_names": {
                            "en": "Moscow",
                            "ru": "Москва"
                        },
                        "lat": 46.7323875,
                        "lon": -117.0001651,
                        "country": "US",
                        "state": "Idaho"
                    },
                    {
                        "name": "Moscow",
                        "lat": 45.071096,
                        "lon": -69.891586,
                        "country": "US",
                        "state": "Maine"
                    },
                    {
                        "name": "Moscow",
                        "lat": 35.0619984,
                        "lon": -89.4039612,
                        "country": "US",
                        "state": "Tennessee"
                    },
                    {
                        "name": "Moscow",
                        "lat": 39.5437014,
                        "lon": -79.0050273,
                        "country": "US",
                        "state": "Maryland"
                    }
                ]
                """;

        Mockito.when(response.body()).thenReturn(JSON_LOCATIONS);

        List<LocationDto> respLocationDto = apiForecastService.searchLocationByName(
                LocationDto.builder()
                        .name("Moscow")
                        .build());

        LocationDto expectedLocation = LocationDto.builder()
                .name("Moscow")
                .lat(LATITUDE)
                .lon(LONGITUDE)
                .country("RU")
                .state("Moscow")
                .build();

        assertEquals(respLocationDto.get(0), expectedLocation);
    }

    @Test
    public void searchCurrentForecastByLocation() {

        final String JSON_FORECAST = """
                {
                      "coord": {
                          "lon": 37.6174943,
                          "lat": 55.7504461
                      },
                      "weather": [
                          {
                              "id": 804,
                              "main": "Clouds",
                              "description": "пасмурно",
                              "icon": "04d"
                          }
                      ],
                      "base": "stations",
                      "main": {
                          "temp": 19.75,
                          "feels_like": 18.82,
                          "temp_min": 18.34,
                          "temp_max": 20.29,
                          "pressure": 1031,
                          "humidity": 40,
                          "sea_level": 1031,
                          "grnd_level": 1012
                      },
                      "visibility": 10000,
                      "wind": {
                          "speed": 5.48,
                          "deg": 256,
                          "gust": 8.67
                      },
                      "clouds": {
                          "all": 95
                      },
                      "dt": 1714463181,
                      "sys": {
                          "type": 1,
                          "id": 9027,
                          "country": "RU",
                          "sunrise": 1714441686,
                          "sunset": 1714496709
                      },
                      "timezone": 10800,
                      "id": 524901,
                      "name": "Москва",
                      "cod": 200
                  }
                """;

        Mockito.when(response.body()).thenReturn(JSON_FORECAST);

        LocationDto locationDto = LocationDto.builder()
                .lat(LATITUDE)
                .lon(LONGITUDE)
                .build();
        CurrentForecastDto forecastDto = apiForecastService.searchCurrentForecastByLocation(locationDto);

        CurrentForecastDto expForDto = new CurrentForecastDto();
        expForDto.setName("Москва");
        expForDto.setCoord(new Coord(LONGITUDE, LATITUDE));
        expForDto.setMain(new Main(19.75, 1031, 40));
        expForDto.setWind(new Wind(5.48, 256, 8.67));
        expForDto.setWeather(List.of(new Weather(804, "Clouds", "пасмурно", "04d")));

        assertEquals(forecastDto, expForDto);
    }

    @Test
    public void searchHourlyForecastByLocation() throws IOException, InterruptedException {

        String JSON_HOURLY_FORECAST = """

                {
                    "cod": "200",
                    "message": 0,
                    "cnt": 40,
                    "list": [
                        {
                            "dt": 1714467600,
                            "main": {
                                "temp": 19.94,
                                "feels_like": 18.93,
                                "temp_min": 19.77,
                                "temp_max": 19.94,
                                "pressure": 1031,
                                "sea_level": 1031,
                                "grnd_level": 1012,
                                "humidity": 36,
                                "temp_kf": 0.17
                            },
                            "weather": [
                                {
                                    "id": 804,
                                    "main": "Clouds",
                                    "description": "пасмурно",
                                    "icon": "04d"
                                }
                            ],
                            "clouds": {
                                "all": 93
                            },
                            "wind": {
                                "speed": 6.01,
                                "deg": 258,
                                "gust": 8.5
                            },
                            "visibility": 10000,
                            "pop": 0,
                            "sys": {
                                "pod": "d"
                            },
                            "dt_txt": "2024-04-30 09:00:00"
                        }
                    ],
                    "city": {
                        "id": 524901,
                        "name": "Москва",
                        "coord": {
                            "lat": 55.7504461,
                            "lon": 37.6174943
                        },
                        "country": "RU",
                        "population": 1000000,
                        "timezone": 10800,
                        "sunrise": 1714441684,
                        "sunset": 1714496708
                    }
                }

                """;

        Mockito.when(response.body()).thenReturn(JSON_HOURLY_FORECAST);

        LocationDto locationDto = LocationDto.builder()
                .lat(LATITUDE)
                .lon(LONGITUDE)
                .build();
        HourlyForecastDTO fcastDto = apiForecastService.searchHourlyForecastByLocation(locationDto);

        HourlyForecast hourFcast = new HourlyForecast();
        hourFcast.setMain(new Main(19.94, 1031, 36));
        hourFcast.setWind(new Wind(6.01, 258, 8.5));
        hourFcast.setDate(LocalDateTime.parse("2024-04-30T09:00:00"));
        hourFcast.setWeather(List.of(new Weather(804, "Clouds", "пасмурно", "04d")));

        HourlyForecastDTO expFcastDto = new HourlyForecastDTO();
        expFcastDto.setList(List.of(hourFcast));
        expFcastDto.setCity(new City(524901, "Москва", new Coord(LONGITUDE, LATITUDE)));

        assertEquals(fcastDto, expFcastDto);
    }

    @Test
    public void checkingStatusCodeApi() {

        String JSON_ERROR = """
                        {"cod":"400","message":"Nothing to geocode"}
                
                """;

        Mockito.when(response.body()).thenReturn(JSON_ERROR);
        Mockito.when(response.statusCode()).thenReturn(400);

        assertThrows(ApiWeatherException.class, () -> {
            apiForecastService.searchLocationByName(
                    LocationDto.builder()
                            .name("Moscow")
                            .build());
        });

        JSON_ERROR = """
                        {"cod":"500","message":"Nothing to geocode"}
                
                """;

        Mockito.when(response.body()).thenReturn(JSON_ERROR);
        Mockito.when(response.statusCode()).thenReturn(500);

        assertThrows(ApiWeatherException.class, () -> {
            apiForecastService.searchLocationByName(
                    LocationDto.builder()
                            .name("Moscow")
                            .build());
        });
    }
}
