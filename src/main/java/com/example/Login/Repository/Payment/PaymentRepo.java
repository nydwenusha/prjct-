package com.example.Login.Repository.Payment;

import com.example.Login.Entity.Payment.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface PaymentRepo extends JpaRepository<PaymentEntity, Long> {
}
