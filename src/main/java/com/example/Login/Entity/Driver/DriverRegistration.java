package com.example.Login.Entity.Driver;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DriverRegistration {
    @Id
    //@Column(length = 45, nullable = false, unique = true)
    private String user_id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String licence_no;

    @Column(nullable = false)
    private String ph_num;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String designation;
}
