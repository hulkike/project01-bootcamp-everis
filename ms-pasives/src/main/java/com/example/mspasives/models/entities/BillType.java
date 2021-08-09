package com.example.mspasives.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Document(collection = "bills_type")
@Getter
@Setter
@AllArgsConstructor
public class BillType {
    @Id
    private String id;
    @NotBlank
    private String name;
}
