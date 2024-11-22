package com.example.Login.Entity.Login;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Guest {
    @Id
    private String guestEmail;

    @Column(nullable = false)
    private String guestName;

    @Column(nullable = false)
    private String guestPhNumber;

    @Column(nullable = false)
    private String password;

    private String designation;



}
