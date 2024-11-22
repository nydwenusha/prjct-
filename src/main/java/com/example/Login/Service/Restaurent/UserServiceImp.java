package com.example.Login.Service.Restaurent;


import com.example.Login.Entity.Restaurant.User;
import com.example.Login.Repository.Restaurant.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
@Service
public class UserServiceImp implements UserService{


    @Autowired
     private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
          String email=jwtProvider.getEmailFromJwtToken(jwt);
          User user=findUserByEmail(email);
          return user;
    }






    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("user not found");
        }
        return user;
    }




}


 */
