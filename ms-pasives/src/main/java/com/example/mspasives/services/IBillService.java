package com.example.mspasives.services;

import com.example.mspasives.models.entities.Bill;
import com.example.mspasives.models.entities.BillType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBillService extends IBaseService<Bill, String>{
    Mono<Bill> findByAccountNumber(String accountNumber);
}
