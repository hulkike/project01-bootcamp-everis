package com.example.mspasives.repositories;

import com.example.mspasives.models.entities.Bill;
import reactor.core.publisher.Mono;

public interface BillRepository extends IRepository<Bill, String>{
    Mono<Bill> findByAccountNumber(String accountNumber);
}
