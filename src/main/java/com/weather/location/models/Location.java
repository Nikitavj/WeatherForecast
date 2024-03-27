package com.weather.location.models;

import com.weather.user.models.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Locations",
        uniqueConstraints = {@UniqueConstraint(
                name = "UniqueUserLatitudeLongitude",
                columnNames = {"userId", "latitude", "longitude"})}
)
public class Location {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    private double latitude;

    private double longitude;
}
