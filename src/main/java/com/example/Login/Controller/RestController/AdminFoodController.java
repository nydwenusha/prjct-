package com.example.Login.Controller.RestController;

import com.example.Login.Dto.Request.Driver.Restaurent.CreateFoodRequest;
import com.example.Login.Dto.Response.Restaurant.MessageResponse;
import com.example.Login.Entity.Restaurant.Food;
import com.example.Login.Entity.Restaurant.Restaurant;
import com.example.Login.Entity.Restaurant.User;
import com.example.Login.Service.Restaurent.FoodService;
import com.example.Login.Service.Restaurent.RestaurantService;
import com.example.Login.Service.Restaurent.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private UserService userService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Food> createFood(@RequestBody CreateFoodRequest req,
                                           @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant=restaurantService.findRestaurantById(req.getRestaurantId());
        Food food= foodService.createFood(req,req.getCategory(),restaurant);

        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteFood(@PathVariable Long id,
                                                      @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwtToken(jwt);

        foodService.deleteFood(id);

        MessageResponse res=new MessageResponse();
        res.setMessage("food deleted successfully");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Food> updateFoodAvailibilityStatus(@PathVariable Long id,
                                                             @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwtToken(jwt);

        Food food= foodService.updateAvailibilityStatus(id);


        return new ResponseEntity<>(food, HttpStatus.CREATED);
    }


}
