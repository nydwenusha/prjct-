package com.example.Login.Dto.Request.Guest;


import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class GuestReq {


    private String guestEmail;
    private String guestName;
    private String guestPhNumber;
    private String password;
}
