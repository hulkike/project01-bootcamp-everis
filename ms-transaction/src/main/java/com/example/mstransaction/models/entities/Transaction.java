package com.example.mstransaction.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "transaction")
@Data
public class Transaction {
    @Id
    private String id;

    @Field(name = "transactionType")
    private String transactionType;

    @Field(name = "transactionAmount")
    private Double transactionAmount;

    @Field(name = "commission")
    private Double commission;

    @Field(name = "account")
    private Bill bill;

    @JsonIgnoreProperties(ignoreUnknown = true)
    @Field(name = "credit_card")
    private CreditCard creditCard;

    @Field(name = "description")
    private String description;

    @Field(name = "transactionDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate = LocalDateTime.now();
}
