package com.example.Login.Dto.Request.Payment;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class PaymentReq {
    private Long Id;
    private String paymentId;
    private  String guestId;
    private String roomId;
    private Date paymentDate;
    private String paymentPurpose;
}
