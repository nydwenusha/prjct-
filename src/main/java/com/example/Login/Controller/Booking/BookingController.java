package com.example.Login.Controller.Booking;

import com.example.Login.Dto.Request.Booking.BookingReq;
import com.example.Login.Service.Booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/Booking")
@CrossOrigin
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/bookingRegister")
    public ResponseEntity<String> BookingRegistry(@RequestBody BookingReq bookingReq){
        String check = bookingService.registerBooking(bookingReq);

        if(check.equals("Successful Booking")){
            return ResponseEntity.ok("Successful booking");
        }

        return ResponseEntity.ok("register unsuccessful");
    }

}
