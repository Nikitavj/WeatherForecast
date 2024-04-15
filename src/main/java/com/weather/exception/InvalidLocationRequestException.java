package com.weather.exception;

public class InvalidLocationRequestException extends RuntimeException{
    public InvalidLocationRequestException(String message) {
        super(message);
    }
}
