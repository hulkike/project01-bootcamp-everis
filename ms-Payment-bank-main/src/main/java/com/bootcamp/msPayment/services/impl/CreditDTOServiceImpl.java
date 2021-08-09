package com.bootcamp.msPayment.services.impl;

import com.bootcamp.msPayment.models.dto.CreditDTO;
import com.bootcamp.msPayment.services.ICreditDTOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreditDTOServiceImpl implements ICreditDTOService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditDTOServiceImpl.class);

    @Autowired
    private WebClient client;


    @Override
    public Mono<CreditDTO> updateCredit(CreditDTO credit) {
        LOGGER.info("initializing Credit Update");

        return client.put()
                .uri("/{id}", Collections.singletonMap("id",credit.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(credit)
                .retrieve()
                .bodyToMono(CreditDTO.class);
    }

    @Override
    public Mono<CreditDTO> findById(String id) {
        Map<String, Object> params = new HashMap<String,Object>();
        LOGGER.info("initializing Credit query");
        params.put("id",id);
        return client.get()
                .uri("/{id}",params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(CreditDTO.class))
                .doOnNext(c -> LOGGER.info("Credit Response: Credit Amounth={}", c.getAmount()));
    }
}
