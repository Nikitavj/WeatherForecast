package com.weather.exception;

public class InvalidLogupException extends RuntimeException {
    public InvalidLogupException(String message) {
        super(message);
    }
}
