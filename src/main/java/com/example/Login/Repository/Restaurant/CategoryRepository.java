package com.example.Login.Repository.Restaurant;


import com.example.Login.Entity.Restaurant.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findByRestaurantId(Long Id);

}
