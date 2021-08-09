package com.example.msdepositbank.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@JsonIgnoreProperties(ignoreUnknown = true)
//@Document(collection = "bill")
@Data
public class Bill {
    //@Id
    //private String id;

    @Field(name = "accountNumber")
    private String accountNumber;

    @Field(name = "balance")
    private Double balance;
}
