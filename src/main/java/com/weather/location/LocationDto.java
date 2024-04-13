package com.weather.location;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class LocationDto {
    private int numberLocForUser;
    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;
}





