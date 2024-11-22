package com.example.Login.Entity.Login;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginSession {
    @Id
    private String sessionId;

    private String userId;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "expires_at")
    private Timestamp expiresAt;

    private String device;
}
