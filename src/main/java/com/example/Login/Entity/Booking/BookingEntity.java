package com.example.Login.Entity.Booking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingEntity {
    @Id
    private String bookingCode; //here we create the booking code

    @Column(nullable = false)
    private String availability; // here we calculate the availability using check in checkout date

    @Column(nullable = false)
    private Integer numberOfGuest; //greater than 18

    private Integer numberOfChild; // less than 18 age

    @Column(nullable = false)
    private LocalDateTime bookingTime;

    @Column(nullable = false)
    private String guestName;

    @Column(nullable = false)
    private String guestEmail;

    @Column(nullable = false)
    private String guestPhNumber;

    @Column(nullable = false)
    private Date checkInD;

    @Column(nullable = false)
    private Date checkOutD;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String fullAddress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipcode;

    private String arrivalTime;

    private String additionalRequest;

    private String roomId;

}
