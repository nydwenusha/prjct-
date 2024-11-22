package com.example.Login.Entity.Property.Room;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {

    @Id
    private String propertyId;

    @Column(nullable = false)
    private String property_name;

    //private Integer roomPrice;

    //private String roomType;//here get as the title of the room(1 king)

    //private Integer numOfGuest;

    private String property_desc;

    private String image_link;

}
