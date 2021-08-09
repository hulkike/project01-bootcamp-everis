package com.bootcamp.msPayment.models.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDTO {

    private String id;
    private double amount;
    private String customerIdentityNumber;
}
