package com.example.Login.Service.Driver;

import com.example.Login.Dto.Request.Driver.DriverReq;
import com.example.Login.Entity.Driver.DriverRegistration;
import com.example.Login.Repository.Driver.DriverRepo;
import com.example.Login.Service.Login.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverService {

    @Autowired
    private final DriverRepo driverRepo;

    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public DriverService(DriverRepo driverRepo, EmployeeService employeeService, PasswordEncoder passwordEncoder) {
        this.driverRepo = driverRepo;
        this.employeeService = employeeService;
        this.passwordEncoder = passwordEncoder;
    }

    public String DriverRegistration (DriverReq driverReq){
        Optional<DriverRegistration> proveEmployee = driverRepo.findById(driverReq.getUser_id());

        if(proveEmployee.isPresent()){
            return "An account with this email already exists.";
        }else{
            String checkEmail = employeeService.validateEmail(driverReq.getUser_id());
            if(checkEmail.equals("Valid email address")){
                if(employeeService.isValidPassword(driverReq.getPassword())){
                    String designation = employeeService.designations(driverReq.getUser_id());

                    DriverRegistration driver = new DriverRegistration();
                        driver.setUser_id(driverReq.getUser_id());
                        driver.setFirstname(driverReq.getFirstname());
                        driver.setLastname(driverReq.getLastname());
                        driver.setLicence_no(driverReq.getLicence_no());
                        driver.setPh_num(driverReq.getPh_num());
                        driver.setPassword(this.passwordEncoder.encode(driverReq.getPassword()));
                        driver.setDesignation(designation);

                    driverRepo.save(driver);
                    Optional<DriverRegistration> proveEmployee1 = driverRepo.findById(driverReq.getUser_id());
                    if(proveEmployee1.isPresent()){
                        DriverRegistration driver2 = proveEmployee1.get();

                        employeeService.sendRegistrationEmail(driver2.getUser_id(),driver2.getFirstname());
                        return "user saved successfully";
                    }
                    return "user saved successfully";
                }else{
                    return "Invalid password";
                }

            }else{
                return "Invalid email";
            }
        }
    }

}
