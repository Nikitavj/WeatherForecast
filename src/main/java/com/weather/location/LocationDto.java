package com.weather.location;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LocationDto {
    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;
}





