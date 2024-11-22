package com.example.Login.Controller.Guest;

import com.example.Login.Dto.Request.Guest.GuestReq;
import com.example.Login.Service.Guest.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/Guest")
@CrossOrigin
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PostMapping("/userRegistration")
    public ResponseEntity<String> userRegistration(@RequestBody GuestReq guestReq){
        String check = guestService.userRegistration(guestReq);
        return ResponseEntity.ok("user registration successful");
    }




}
