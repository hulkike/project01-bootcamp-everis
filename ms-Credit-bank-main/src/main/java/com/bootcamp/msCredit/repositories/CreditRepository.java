package com.bootcamp.msCredit.repositories;

import com.bootcamp.msCredit.models.entities.Credit;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CreditRepository extends ReactiveMongoRepository<Credit,String> {
    public Flux<Credit> findAllByCustomerIdentityNumber(String customerIdentityNumber);
}
