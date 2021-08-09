package com.bootcamp.msCredit.models.entities;

import com.bootcamp.msCredit.models.dto.CustomerDTO;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "creditcustomer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCustomer {

    @Id
    private String id;

    private Credit credit;

    private CustomerDTO customer;
}
