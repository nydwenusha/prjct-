package com.example.Login.Dto.Request.Booking;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class BookingReq {

    private String guestName;
    private String guestEmail;
    private String guestPhNumber;
    private Integer numberOfGuest; //greater than 18
    private Integer numberOfChild; // less than 18 age
    private Date checkInD;
    private Date checkOutD;
    private String country;
    private String fullAddress;
    private String city;
    private String state;
    private String zipcode;
    private String arrivalTime;
    private String additionalRequest;
    private String roomId;

}
