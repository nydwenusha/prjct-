package com.example.Login.Controller.PaymentController;

import com.example.Login.Dto.Request.Payment.PaymentReq;
import com.example.Login.Service.Payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/bookingPayment")
    public ResponseEntity<String> bookingPayment(@RequestBody PaymentReq paymentReq){
        paymentService.bookingRegistrySave(paymentReq);
        return ResponseEntity.ok("payment is successful");
    }
}
