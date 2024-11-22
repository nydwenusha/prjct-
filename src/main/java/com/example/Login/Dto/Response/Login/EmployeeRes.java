package com.example.Login.Dto.Response.Login;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeRes {
    
    private String firstname;
    private String lastname;
    private String user_id;
    private String destination;
    private String ph_num;

}
