package com.example.Login.Service.Restaurent;



import com.example.Login.Dto.Request.Driver.Restaurent.CreateFoodRequest;
import com.example.Login.Entity.Restaurant.Category;
import com.example.Login.Entity.Restaurant.Food;
import com.example.Login.Entity.Restaurant.Restaurant;

import java.util.List;


public interface FoodService {

    public Food createFood(CreateFoodRequest req, Category category, Restaurant restaurant );

    void deleteFood(Long foodId) throws Exception;

    public List<Food> getRestaurantsFood(Long restaurantId,
                                         boolean isVegetarian,
                                         boolean isNonveg,
                                         boolean isSeasonal,
                                         String foodCategory
    );

    public List<Food> searchFood(String keyword);

    public Food findFoodById(Long foodId) throws Exception;

    public Food updateAvailibilityStatus(Long foodId) throws Exception;

}
