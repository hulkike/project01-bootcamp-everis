package com.bootcamp.msCreditcard.models.entities;

import com.bootcamp.msCreditcard.models.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "creditcard")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCard {

    @Id
    private String id;

    @NotNull
    @Indexed(unique=true)
    private String pan;

    @NotNull
    private String cardType;

    @Field( name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();

    @NotNull
    private String cardBran;

    @NotNull
    private String creditCardType;

    private double balanceAmount;

    @NotNull
    private double creditLimit;

    private double totalConsumption;

    @NotNull
    private int settlementDay;

    @NotNull
    private int chargeDay;

    @NotNull
    private CustomerDTO customer;
}
