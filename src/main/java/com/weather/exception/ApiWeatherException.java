package com.weather.exception;

public class ApiWeatherException extends RuntimeException{
    public ApiWeatherException(String message) {
        super(message);
    }
}
