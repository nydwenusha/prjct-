package com.example.Login.Service.Payment;

import com.example.Login.Dto.Request.Payment.PaymentReq;
import com.example.Login.Entity.Payment.PaymentEntity;
import com.example.Login.Repository.Payment.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private final PaymentRepo paymentRepo;

    public PaymentService(PaymentRepo paymentRepo) {
        this.paymentRepo = paymentRepo;
    }

    public void bookingRegistrySave(PaymentReq paymentReq) {
        PaymentEntity paymentEntity = new PaymentEntity();


        paymentRepo.save(paymentEntity);

    }
}
