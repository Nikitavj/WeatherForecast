package com.weather.exception;

import lombok.Data;

@Data
public class ErrorApi {
    private int code;
    private String message;
}
