package com.weather.location;

import com.weather.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Locations")
public class Location {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;

    private double latitude;

    private double longitude;
}
