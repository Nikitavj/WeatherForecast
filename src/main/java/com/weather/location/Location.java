package com.weather.location;

import com.weather.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Locations")
public class Location {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToOne
    @JoinColumn(name = "UserId")
    private User user;

    private double latitude;

    private double longitude;
}
