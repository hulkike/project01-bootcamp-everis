package com.bootcamp.msCredit.models.dto;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {


    private String name;
    private String customerIdentityType;
    private String customerIdentityNumber;
}
