package com.example.Login.Dto;


import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class EmployeeDto {

    private String firstname;
    private String lastname;
    private String user_id;
    private String ph_num;
    private String password;


}
