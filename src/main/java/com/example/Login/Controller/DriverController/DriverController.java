package com.example.Login.Controller.DriverController;

import com.example.Login.Dto.Request.Driver.DriverReq;
import com.example.Login.Service.Driver.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/Driver")
@CrossOrigin
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/DriverRegistration")
    public String saveDriver(@RequestBody DriverReq driverReq){

        String registry = driverService.DriverRegistration(driverReq);

        return switch (registry) {
            case "An account with this email already exists." -> "An account with this email already exists.";
            case "Invalid email" -> "Invalid email";
            case "Invalid password" -> "Invalid password";
            default -> "user saved successfully";
        };
    }
}
