package com.example.Login.Dto.Response.Restaurant;

import com.example.Login.Entity.Restaurant.USER_ROLE;
//import com.wen1.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;

    public void setJwt(String jwt) {
    }

    public void setMessage(String registerSuccess) {
    }

    public void setRole(Object role) {

    }
}
