package com.example.Login.Repository.Restaurant;


import com.example.Login.Entity.Restaurant.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {


}
