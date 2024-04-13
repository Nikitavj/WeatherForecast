package com.weather.forecast.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.exception.ApiWeatherErrorException;
import com.weather.exception.ApiWeatherNotFoundException;
import com.weather.exception.ErrorApi;
import com.weather.forecast.ForecastDto;
import com.weather.location.LocationDto;
import com.weather.utils.PropertiesUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
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
        apiKey = PropertiesUtil.getProperty("weatherApyKey");
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public ApiForecastServiceImpl(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<LocationDto> searchLocationByName(LocationDto location) {

        try {
            String urlName = URLEncoder.encode(location.getName(), "UTF-8");
            String uri = String.format("http://api.openweathermap.org/geo/1.0/direct?q=%s&limit=5&appid=%s",
                    urlName,
                    apiKey);

            String json = sendApiRequest(uri);
            LocationDto[] locations = objectMapper.readValue(json, LocationDto[].class);
            return Arrays.asList(locations);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ForecastDto searchForecastByLocation(LocationDto location) {

        String uri = String.format("https://api.openweathermap.org/data/2.5/weather?units=metric&lat=%s&lon=%s&appid=%s&lang=ru",
                location.getLat(),
                location.getLon(),
                apiKey);

        String json = sendApiRequest(uri);

        try {
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

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int status = response.statusCode();

            if (status/100 == 4) {
                ErrorApi e = objectMapper.readValue(response.body(), ErrorApi.class);
                throw new ApiWeatherNotFoundException(e.getMessage());
            }

            if (status/100 == 5) {
                ErrorApi e = objectMapper.readValue(response.body(), ErrorApi.class);
                throw new ApiWeatherErrorException(e.getMessage());
            }

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

