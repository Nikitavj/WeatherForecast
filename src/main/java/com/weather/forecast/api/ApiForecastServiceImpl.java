package com.weather.forecast.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.weather.exception.ApiWeatherException;
import com.weather.exception.ApiWeatherNotFoundException;
import com.weather.exception.ErrorApi;
import com.weather.forecast.api.dto.CurrentForecastDto;
import com.weather.forecast.api.dto.HourlyForecastDTO;
import com.weather.forecast.location.LocationDto;
import com.weather.utils.PropertiesUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class ApiForecastServiceImpl implements ApiForecastService {
    private HttpClient httpClient;
    private static String apiKey;
    private static ObjectMapper objectMapper;

    static {
        apiKey = System.getenv("KEY_API_WEATHER");
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ApiForecastServiceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<LocationDto> searchLocationByName(LocationDto location) {

        try {
            String urlName = URLEncoder.encode(location.getName(), "UTF-8");
            URI uri = URI.create("http://api.openweathermap.org/geo/1.0/direct?"
                    + "q=" + urlName
                    + "&limit=5"
                    + "&appid=" + apiKey);
            String json = sendApiRequest(uri);

            LocationDto[] locations = objectMapper.readValue(json, LocationDto[].class);
            return Arrays.asList(locations);

        } catch (UnsupportedEncodingException
                 | JsonProcessingException e) {
            throw new ApiWeatherException();
        }
    }

    @Override
    public CurrentForecastDto searchCurrentForecastByLocation(LocationDto location) {

        URI uri = URI.create("https://api.openweathermap.org/data/2.5/weather?"
                + "units=metric"
                + "&lat=" + location.getLat()
                + "&lon=" + location.getLon()
                + "&appid=" + apiKey
                + "&lang=ru");
        String json = sendApiRequest(uri);

        try {
            return objectMapper.readValue(json, CurrentForecastDto.class);

        } catch (JsonProcessingException e) {
            throw new ApiWeatherException();
        }
    }

    @Override
    public HourlyForecastDTO searchHourlyForecastByLocation(LocationDto location) {

        URI uri = URI.create("https://api.openweathermap.org/data/2.5/forecast?"
                + "units=metric"
                + "&lat=" + location.getLat()
                + "&lon=" + location.getLon()
                + "&appid=" + apiKey
                + "&lang=ru");
        String json = sendApiRequest(uri);

        try {
            return objectMapper.readValue(json, HourlyForecastDTO.class);

        } catch (JsonProcessingException e) {
            throw new ApiWeatherException();
        }
    }

    private String sendApiRequest(URI uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();

            if (status  == HttpServletResponse.SC_NOT_FOUND) {
                ErrorApi e = objectMapper.readValue(response.body(), ErrorApi.class);
                throw new ApiWeatherNotFoundException("Not Found: " + e.getCode());
            }

            status = (int) Math.round(status / 100) * 100;

            if (status == HttpServletResponse.SC_BAD_REQUEST) {
                ErrorApi e = objectMapper.readValue(response.body(), ErrorApi.class);
                throw new ApiWeatherException("Client error: " + e.getCode());
            }

            if (status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
                ErrorApi e = objectMapper.readValue(response.body(), ErrorApi.class);
                throw new ApiWeatherException("Server error: " + e.getCode());
            }
            return response.body();

        } catch (InterruptedException | IOException e) {
            throw new ApiWeatherException();
        }
    }
}

