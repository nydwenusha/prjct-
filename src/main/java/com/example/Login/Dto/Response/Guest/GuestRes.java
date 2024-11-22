package com.example.Login.Dto.Response.Guest;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GuestRes {

    private String guestEmail;
    private String guestName;
    private String guestPhNumber;
    private String password;
}
