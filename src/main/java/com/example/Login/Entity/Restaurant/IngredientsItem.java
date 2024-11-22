package com.example.Login.Entity.Restaurant;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    private IngredientCategory category;

    @JsonIgnore
    @ManyToOne
    private Restaurant restaurant;

    private boolean inStock=true;

    //---------------------------------------------------------




    // Corrected method implementations
    public void setName(String ingredientName) {
        this.name = ingredientName;
    }

    public void setCategory(IngredientCategory category) {
        this.category = category;
    }

    public void setInStock(boolean inStock) {  // Corrected name
        this.inStock = inStock;
    }

    public boolean isInStock() {  // Corrected name
        return inStock;
    }




    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
