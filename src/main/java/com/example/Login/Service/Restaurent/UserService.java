package com.example.Login.Service.Restaurent;

//import com.wen1.model.User;
import org.springframework.security.core.userdetails.User;

public interface UserService {

    public User findUserByJwtToken(String jwt) throws Exception;

    //public User FindUserByEmail(String email) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
