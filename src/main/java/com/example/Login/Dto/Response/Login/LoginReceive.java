package com.example.Login.Dto.Response.Login;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginReceive {
    private String user_id;
    private String password;
}
