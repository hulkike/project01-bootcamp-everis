package com.example.msretire.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "retire")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Retire {
    @Id
    private String id;

    @Field(name = "bill")
    private Bill bill;

    @Field(name = "amount")
    private Double amount;

    @Field(name = "description")
    private String description = "";

    @Field(name = "retireDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime retireDate = LocalDateTime.now();
}
