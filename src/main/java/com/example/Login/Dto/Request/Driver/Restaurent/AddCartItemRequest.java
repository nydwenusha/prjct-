package com.example.Login.Dto.Request.Driver.Restaurent;

import lombok.Data;

import java.util.List;

@Data
public class AddCartItemRequest {

    private Long foodId;

    private int quantity;

    private List<String> ingredients;

    //----------------------------------------------------------

    public Long getFoodId() {
        return foodId;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public Object getIngredients() {
        return ingredients;
    }


}
