package com.weather.location;

import com.weather.exception.InvalidLocationRequestException;
import org.thymeleaf.util.NumberUtils;


public class LocationValidatorUtil {

    public static void validateNameLocation(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidLocationRequestException("Отсутствует название искомой локации");
        }
    }
}
