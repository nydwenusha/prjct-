package com.example.Login.Dto.Request.Driver.Restaurent;


import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;


    //-----------------------------------------------
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
