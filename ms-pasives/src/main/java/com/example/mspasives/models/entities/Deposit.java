package com.example.mspasives.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "deposit")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Deposit {
    @Id
    private String id;

    @Field(name = "bill")
    private Bill bill;

    @Field(name = "depositAmount")
    private Double depositAmount;

    @Field(name = "description")
    private String description;

    @Field(name = "depositDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime depositDate;
}
