package com.bootcamp.msConsumptions.models.entities;

import com.bootcamp.msConsumptions.models.dto.CreditCardDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "consumption")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Consumption {

    @Id
    private String id;

    private double amount;

    private String description;

    private final String TYPEOFTRANSACTION = "CONSUMPTION";

    private String identityNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();
}
