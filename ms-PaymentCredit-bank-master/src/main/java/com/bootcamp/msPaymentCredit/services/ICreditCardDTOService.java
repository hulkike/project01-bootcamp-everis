package com.bootcamp.msPaymentCredit.services;

import com.bootcamp.msPaymentCredit.models.dto.CreditCardDTO;
import reactor.core.publisher.Mono;

public interface ICreditCardDTOService {
    public Mono<CreditCardDTO> updateCredit(CreditCardDTO credit);
    public Mono<CreditCardDTO> findByPan(String pan);
}
