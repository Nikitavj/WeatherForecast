package com.weather.exception;

public class ApiWeatherNotFoundException extends RuntimeException{
    public ApiWeatherNotFoundException(String message) {
        super(message);
    }
}
