package com.example.Login.Entity.Login;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    //@Column(length = 45, nullable = false, unique = true)
    private String user_id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String ph_num;

    @Column(nullable = false)
    private String password;

    private String profile_url;

    @Column(nullable = false)
    private String designation;

    @OneToOne(mappedBy = "employee",fetch = FetchType.LAZY)
    private ForgotPassword forgotPassword;
    //private String gender;
    //private String first_name;
    //private String last_name;

}
