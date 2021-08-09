package com.bootcamp.msConsumptions.models.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "creditCardDTO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCardDTO {


    private String id;
    private String pan;
    private double balanceAmount;
    private double totalConsumption;
    private String customerIdentityNumber;
    private double creditLimit;

}
