package com.example.Login.Repository.Restaurant;


import com.example.Login.Entity.Restaurant.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


}
