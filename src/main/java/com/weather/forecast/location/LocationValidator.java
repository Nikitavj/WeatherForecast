package com.weather.forecast.location;

import com.weather.exception.InvalidLocationRequestException;

public class LocationValidator {

    public static void validateNameLocation(String name) {
        if (name == null || name.isBlank()) {
            throw new InvalidLocationRequestException(
                    "The name of the location you are looking for is missing");
        }
    }
}
