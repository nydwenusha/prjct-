package com.example.Login.Dto.RestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.List;

@Data
@Embeddable
public class RestaurantDto {

    private String title;

    @Column(length = 1000)
    private List<String> images;

    private String description;
    private Long id;

    public void setDescription(boolean description) {
    }

    public void setImages(Object images) {
    }

    public void setTitle(boolean name) {
    }

    public void setId(Long restaurantId) {
    }

    public Object getId() {
        return id;
    }
}
