package com.weather.forecastapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.location.Location;
import com.weather.location.LocationDto;
import com.weather.utils.PropertiesUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class ApiForecastServiceImpl implements ApiForecastService {
    private String apiKey = PropertiesUtil.getProperty("weatherApyKey");
    @Override
    public List<LocationDto> searchLocationByCityName(String nameCity) {

        String uri = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s",
                nameCity,
                apiKey);

        String json = sendApiRequest(uri);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            LocationDto[] locations = objectMapper.readValue(json, LocationDto[].class);
            return Arrays.asList(locations);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ForecastDto searchForecastByLocation(LocationDto location) {

        String uri = String.format("https://api.openweathermap.org/data/2.5/weather?units=metric&lat=%s&lon=%s&appid=%s",
                location.getLat(),
                location.getLon(),
                apiKey);

        String json = sendApiRequest(uri);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(json, ForecastDto.class);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String sendApiRequest(String uri) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(uri))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient
                    .newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
