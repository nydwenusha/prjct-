package com.example.Login.Entity.Restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JsonIgnore
    private Cart cart;

    @ManyToOne
    private Food food;

    private int quantity;

    private List<String> ingredients;

    private Long totalPrice;

    //---------------------------------------------------------

    public Food getFood() {
        return food;
    }

    public int getQuantity() {
        return quantity;
    }


    public Long getId() {
        return id;
    }

    public void setFood(Food food) {
    }

    public void setCart(Cart cart) {
    }

    public void setQuantity(int quantity) {
    }

    public void setIngredients(Object ingredients) {
    }



    public void setTotalPrice(Long totalPrice) {
      this.totalPrice = totalPrice;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public List<String> getIngredients() {
        return ingredients;
    }
}
