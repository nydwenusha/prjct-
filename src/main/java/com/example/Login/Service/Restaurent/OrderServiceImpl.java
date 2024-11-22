package com.example.Login.Service.Restaurent;


import com.example.Login.Dto.Request.Driver.Restaurent.OrderRequest;
import com.example.Login.Entity.Restaurant.*;
import com.example.Login.Repository.Restaurant.AddressRepository;
import com.example.Login.Repository.Restaurant.OrderItemRepository;
import com.example.Login.Repository.Restaurant.OrderRepository;
import com.example.Login.Repository.Restaurant.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private CartService cartService;


    @Override
    public Order createOrder(OrderRequest order, User user) {

        Address shippAddress=order.getDeliveryAddress();

        Address savedAddress=addressRepository.save(shippAddress);

       /* if(!user.getAddresses().contains(savedAddress)){
            user.getAddresses().add(savedAddress);
            userRepository.save(user);
        }  */

        Restaurant restaurant= null;
        try {
            restaurant = restaurantService.findRestaurantById(order.getRestaurantId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Order createdOrder=new Order();
        createdOrder.setCustomer(user);
        createdOrder.setCreatedAt(new Date());
        createdOrder.setOrderStatus("PENDING");
        createdOrder.setDeliveryAddress(savedAddress);
        createdOrder.setRestaurant(restaurant);

        Cart cart= null;
        try {
            cart = cartService.findCartByUserId(user.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List<OrderItem> orderItems=new ArrayList<>();

        for(CartItem cartItem : cart.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setFood(cartItem.getFood());
            orderItem.setIngredients(cartItem.getIngredients());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getTotalPrice());

            OrderItem savedOrderItem=orderItemRepository.save(orderItem);
            orderItems.add(savedOrderItem);
            
        }
        Long totalPrice= null;
        try {
            totalPrice = cartService.calculateCartTotals(cart);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        createdOrder.setItems(orderItems);
        createdOrder.setTotalPrice(totalPrice);

        Order savedOrder=orderRepository.save(createdOrder);
        restaurant.getOrders().add(savedOrder);


        return createdOrder;
    }

    @Override
    public Order updateOrder(Long orderId, String orderStatus) throws Exception {
        Order order=findOrderById(orderId);
        if(orderStatus.equals("OUT_FOR_DELIVERY")
                || orderStatus.equals("DELIVERED")
                || orderStatus.equals("COMPLETED")
                || orderStatus.equals("PENDING")
        ){
           order.setOrderStatus(orderStatus);
           return orderRepository.save(order);
        }
        throw new Exception("Please select a valid order status");
    }

    @Override
    public void calcelOrder(Long orderId) throws Exception {

        Order order = findOrderById(orderId);
        orderRepository.deleteById(orderId);

    }

    @Override
    public List<Order> getUsersOrder(Long userId) throws Exception {
        return orderRepository.findByCustomerId(userId);
    }

    @Override
    public List<Order> getRestaurantsOrder(Long restaurantId, String orderStatus) throws Exception {
        List<Order> orders= orderRepository.findByRestaurantId(restaurantId);
        if(orderStatus!=null){
            orders = orders.stream().filter(order->
                    order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
        }
        return orders;

    }

    @Override
    public Order findOrderById(Long orderId) throws Exception {
        Optional<Order> optionalOrder=orderRepository.findById(orderId);
        if(optionalOrder.isEmpty()){
            throw new Exception("order not found");
        }
        return optionalOrder.get();
    }
}
