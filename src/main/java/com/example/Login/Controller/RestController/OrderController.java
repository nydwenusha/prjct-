package com.example.Login.Controller.RestController;


import com.example.Login.Dto.Request.Driver.Restaurent.OrderRequest;
import com.example.Login.Entity.Restaurant.Order;
import com.example.Login.Entity.Restaurant.User;
import com.example.Login.Service.Restaurent.OrderService;
import com.example.Login.Service.Restaurent.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest req,
                                             @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        Order order=orderService.createOrder(req,user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


    @GetMapping("/order/user")
    public ResponseEntity<List<Order>> getOrderHistory(
                                        @RequestHeader("Authorization") String jwt) throws Exception{
        User user=userService.findUserByJwtToken(jwt);
        List<Order> orders=orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
