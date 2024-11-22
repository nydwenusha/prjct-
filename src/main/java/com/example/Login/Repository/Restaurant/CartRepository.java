package com.example.Login.Repository.Restaurant;


import com.example.Login.Entity.Restaurant.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {

    public Cart findByCustomerId(Long userId);
}
