package com.bootcamp.msCredit.models.entities;

import com.bootcamp.msCredit.models.dto.CustomerDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "credit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Credit {

    @Id
    private String id;

    @NotNull
    private double capital;

    @Field ( name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();

    @NotNull
    private String customerIdentityNumber;

    private double amount;

    private double commission;

    private double interestRate;

    private double creditLifeIns;

    @NotNull
    private CustomerDTO customer;
}
