package com.example.Login.Service.Guest;

import com.example.Login.Dto.Request.Guest.GuestReq;
import com.example.Login.Entity.Driver.DriverRegistration;
import com.example.Login.Entity.Login.Guest;
import com.example.Login.Repository.Guest.GuestRepo;
import com.example.Login.Service.Login.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuestService {

    @Autowired
    private final GuestRepo guestRepo;

    @Autowired
    private final EmployeeService employeeService;

    public GuestService(GuestRepo guestRepo, EmployeeService employeeService) {
        this.guestRepo = guestRepo;
        this.employeeService = employeeService;
    }

    public String userRegistration(GuestReq guestReq) {
        Optional<Guest> guestPerson = guestRepo.findById(guestReq.getGuestEmail());

        if(guestPerson.isPresent()){
            return "An account with this email already exists.";
        }else{
            String checkEmail = employeeService.validateEmail(guestReq.getGuestEmail());
            if(checkEmail.equals("Valid email address")){
                if(employeeService.isValidPassword(guestReq.getPassword())){
                    String designation = employeeService.designations(guestReq.getGuestEmail());

                    Guest guest = new Guest();
                        guest.setGuestEmail(guestReq.getGuestEmail());
                        guest.setGuestName(guestReq.getGuestName());
                        guest.setGuestPhNumber(guestReq.getGuestPhNumber());
                        guest.setPassword(guestReq.getPassword());
                        guest.setDesignation(designation);

                    guestRepo.save(guest);

                    Optional<Guest> guestPerson1 = guestRepo.findById(guestReq.getGuestEmail());

                    if(guestPerson1.isPresent()){
                        Guest guest2 = guestPerson1.get();

                        employeeService.sendRegistrationEmail(guest2.getGuestEmail(),guest2.getGuestName());
                        return "user saved successfully";
                    }
                    return "user saved successfully";
                }else{
                    return "Invalid password";
                }
            }else {
                return "Invalid email";
            }
        }
    }
}
