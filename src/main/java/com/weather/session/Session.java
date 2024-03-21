package com.weather.session;

import com.weather.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Sessions")
public class Session {
    @Id
    @GeneratedValue
    private String id;

    @OneToOne
    @JoinColumn(name = "UserId")
    private User user;

    private LocalDateTime expiresAt;
}
