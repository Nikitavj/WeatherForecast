package com.weather.exception;

public class ApiWeatherErrorException extends RuntimeException{
    public ApiWeatherErrorException(String message) {
        super(message);
    }
}
