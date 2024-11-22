package com.example.Login.Dto.Request.Property.Room;

import jakarta.persistence.Column;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class RoomReq {

    private String property_id;
    private String property_name;
    private Integer roomPrice;
    private String property_desc;


}
