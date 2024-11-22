package com.example.Login.Repository.Restaurant;
//import com.wen1.model.User;
import com.example.Login.Entity.Restaurant.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    public User findByEmail(String username);
}
