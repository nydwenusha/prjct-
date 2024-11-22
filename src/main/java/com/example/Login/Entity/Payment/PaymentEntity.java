package com.example.Login.Entity.Payment;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String paymentId;

    @Column(nullable = false)
    private  String guestId;

    private String roomId;

    @Column(nullable = false)
    private Date paymentDate;

    @Column(nullable = false)
    private String paymentPurpose;

    @Column(nullable = false)
    private Integer paymentAmount;
}
