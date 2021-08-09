package com.bootcamp.msCreditcard.repositories;

import com.bootcamp.msCreditcard.models.entities.CreditCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CreditCardRepository extends ReactiveMongoRepository<CreditCard,String> {
    Mono<CreditCard> findByPan (String pan);
}
