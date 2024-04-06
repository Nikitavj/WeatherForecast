package com.weather.location.models;

import com.weather.user.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    private double latitude;

    private double longitude;
}
