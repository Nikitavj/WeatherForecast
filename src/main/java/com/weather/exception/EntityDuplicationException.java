package com.weather.exception;

public class EntityDuplicationException extends RuntimeException{
    public EntityDuplicationException(String message) {
        super(message);
    }
}
