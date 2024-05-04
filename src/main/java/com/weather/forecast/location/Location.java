package com.weather.forecast.location;

import com.weather.account.user.User;
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
                columnNames = {"userId", "latitude", "longitude"})},
        indexes = {@Index(columnList = "user")}
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
