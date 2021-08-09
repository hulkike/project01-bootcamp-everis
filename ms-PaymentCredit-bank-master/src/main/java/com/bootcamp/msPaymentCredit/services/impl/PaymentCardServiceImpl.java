package com.bootcamp.msPaymentCredit.services.impl;

import com.bootcamp.msPaymentCredit.models.entities.PaymentCard;
import com.bootcamp.msPaymentCredit.repositories.PaymentCardRepository;
import com.bootcamp.msPaymentCredit.services.IPaymentCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PaymentCardServiceImpl implements IPaymentCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentCardServiceImpl.class);

    @Autowired
    private PaymentCardRepository repository;

    @Override
    public Mono<PaymentCard> create(PaymentCard o) {
        return repository.save(o);
    }

    @Override
    public Flux<PaymentCard> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<PaymentCard> findById(String s) {
        return repository.findById(s);
    }

    @Override
    public Mono<PaymentCard> update(PaymentCard o) {
        return repository.save(o);
    }

    @Override
    public Mono<Void> delete(PaymentCard o) {
        return repository.delete(o);
    }
}
