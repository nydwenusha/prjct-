package com.example.Login.Dto.Request.Driver;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class DriverReq {

    private String user_id;
    private String firstname;
    private String lastname;
    private String licence_no;
    private String ph_num;
    private String password;

}
