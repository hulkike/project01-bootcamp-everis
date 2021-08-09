package com.bootcamp.msPayment.services;

import com.bootcamp.msPayment.models.dto.CreditDTO;
import reactor.core.publisher.Mono;

public interface ICreditDTOService {
    public Mono<CreditDTO> updateCredit(CreditDTO credit);
    public Mono<CreditDTO> findById(String id);
}
