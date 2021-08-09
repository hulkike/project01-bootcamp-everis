package com.bootcamp.msCredit.services.impl;

import com.bootcamp.msCredit.handler.CreditHandler;
import com.bootcamp.msCredit.models.dto.CustomerDTO;
import com.bootcamp.msCredit.models.entities.Credit;
import com.bootcamp.msCredit.repositories.CreditRepository;
import com.bootcamp.msCredit.services.ICreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class CreditServiceImpl implements ICreditService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditServiceImpl.class);

    @Autowired
    private CreditRepository repository;

    @Autowired
    private  WebClient client;

    @Override
    public Mono<Credit> create(Credit credit) {
        return repository.save(credit);
    }

    @Override
    public Flux<Credit> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Credit> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Credit> update( Credit credit) {
        return repository.save(credit);
    }

    @Override
    public Mono<Void> delete(Credit credit) {
        return repository.delete(credit);
    }


    @Override
    public Mono<CustomerDTO> getCustomer(String customerIdentityNumber){
        Map<String, Object> params = new HashMap<String,Object>();
        LOGGER.info("initializing client query");
        params.put("customerIdentityNumber",customerIdentityNumber);
        return client.get()
                .uri("/findCustomerCredit/{customerIdentityNumber}",customerIdentityNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(CustomerDTO.class))
                .doOnNext(c -> LOGGER.info("Customer Response: Customer={}", c.getName()));
    }

    @Override
    public Flux<Credit> findAllByCustomerIdentityNumber(String customerIdentityNumber) {
        return repository.findAllByCustomerIdentityNumber(customerIdentityNumber);
    }
}
