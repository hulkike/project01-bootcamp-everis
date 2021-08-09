package com.example.mspasives.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Document(collection = "bill")
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
public class Bill {
    @Id
    private String id;

    @Field(name = "accountNumber")
    private String accountNumber;

    @Field(name = "dateOpened")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOpened = LocalDateTime.now();;

    @Field(name = "balance")
    private double balance;

    @Field(name = "limitMovementsMonth")
    private int limitMovementsMonth;
}
