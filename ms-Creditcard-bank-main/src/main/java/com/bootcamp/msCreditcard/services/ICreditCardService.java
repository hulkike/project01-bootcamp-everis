package com.bootcamp.msCreditcard.services;

import com.bootcamp.msCreditcard.models.dto.CustomerDTO;
import com.bootcamp.msCreditcard.models.entities.CreditCard;
import reactor.core.publisher.Mono;

public interface ICreditCardService extends ICRUDService<CreditCard,String> {

    public Mono<CustomerDTO> getCustomer(String customerIdentityNumber);
    public Mono<CreditCard> findByPan(String pan);
}
