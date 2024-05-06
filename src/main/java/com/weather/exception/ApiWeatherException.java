package com.weather.exception;

public class ApiWeatherException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Weather forecast search service error";

    public ApiWeatherException(String message) {
        super(message);
    }

    public ApiWeatherException() {
        super(DEFAULT_MESSAGE);
    }
}
