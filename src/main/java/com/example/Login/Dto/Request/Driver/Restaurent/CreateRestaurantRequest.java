package com.example.Login.Dto.Request.Driver.Restaurent;

//import com.wen1.model.Address;
//import com.wen1.model.ContactInformation;
import com.example.Login.Entity.Restaurant.Address;
import com.example.Login.Entity.Restaurant.ContactInformation;
import lombok.Data;

import java.util.List;

@Data
public class CreateRestaurantRequest {

    private Long id;
    private Address address;
    private ContactInformation contactInformation;
    private String cuisineType;
    private String description;
    private List<String> images;
    private String name;
    private String openingHours;

    //----------------------------------------------

    public Address getAddress() {

         return address;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getOpeningHours() {
        return openingHours;
    }
}
