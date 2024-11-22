package com.example.Login.Service.Restaurent;


import com.example.Login.Entity.Restaurant.USER_ROLE;
import com.example.Login.Entity.Restaurant.User;
import com.example.Login.Repository.Restaurant.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user=userRepository.findByEmail(username);
        if(user==null){
               throw new UsernameNotFoundException("user not found with email"+username);
        }

        USER_ROLE role= (USER_ROLE) user.getRole();

        List<GrantedAuthority> authorities=new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), (String) user.getPassword(),authorities);
    }
}
