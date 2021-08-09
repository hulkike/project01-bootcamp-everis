package com.example.msacquisitionbank.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "acquisition")
@Data
public class Acquisition {
    @Id
    private String id;

    @Field(name = "product")
    private Product product;

    @Field(name = "customerOwner")
    private List<Customer> customerHolder;

    @Field(name = "authorizedSigner")
    private List<Customer> customerAuthorizedSigner;

    @Field(name = "initial")
    private double initial;

    @Field(name = "cardNumber")
    private String cardNumber;

    @Field(name = "acquisitionDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime acquisitionDate = LocalDateTime.now();
}
