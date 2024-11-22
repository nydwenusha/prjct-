package com.example.Login.Dto.Response.Property.Room;

import lombok.*;

import java.io.InputStream;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomRes {

    private String roomId;
    private String property_name;
    private String property_desc;
    private String imageStream;

}
