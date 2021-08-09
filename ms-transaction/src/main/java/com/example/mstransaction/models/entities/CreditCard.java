package com.example.mstransaction.models.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "credit_card")
@Data
public class CreditCard {
    @Id
    private String id;

    @Field(name = "cardNumber")
    private String cardNumber;

    @Field(name = "amount")
    private Double amount;
}
