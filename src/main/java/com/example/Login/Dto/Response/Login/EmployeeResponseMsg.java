package com.example.Login.Dto.Response.Login;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class EmployeeResponseMsg {

    String message;
    String login_uuid;
    Boolean Status;
}
