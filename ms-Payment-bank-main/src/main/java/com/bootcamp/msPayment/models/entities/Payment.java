package com.bootcamp.msPayment.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "payment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Payment {

    @Id
    private String id;

    private String identityNumber;

    private final String TYPEOFTRANSACTION = "PAYMENT";

    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();


}
