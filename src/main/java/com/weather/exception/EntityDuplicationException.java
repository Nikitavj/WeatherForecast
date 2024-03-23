package com.weather.exception;

import java.awt.event.FocusEvent;

public class EntityDuplicationException extends RuntimeException{
    public EntityDuplicationException(String message) {
        super(message);
    }
}
