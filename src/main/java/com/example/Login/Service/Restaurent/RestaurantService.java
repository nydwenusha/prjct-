package com.example.Login.Service.Restaurent;


import com.example.Login.Dto.Request.Driver.Restaurent.CreateRestaurantRequest;
import com.example.Login.Dto.RestDto.RestaurantDto;
import com.example.Login.Entity.Restaurant.Restaurant;
import com.example.Login.Entity.Restaurant.User;
//import com.wen1.dto.RestaurantDto;
//import com.wen1.model.Restaurant;
//import com.wen1.model.User;
//import com.wen1.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);

    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant(String keyword);

    public Restaurant findRestaurantById(Long id) throws Exception;

    public Restaurant getRestaurantByUserId(Long userId) throws Exception;

    public RestaurantDto addToFavorites(Long restaurantId, User user)throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;
}
