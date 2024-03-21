package com.weather.user;

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
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String login;

    private String password;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
