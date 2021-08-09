package com.bootcamp.msPaymentCredit.services.impl;

import com.bootcamp.msPaymentCredit.models.dto.CreditCardDTO;
import com.bootcamp.msPaymentCredit.services.ICreditCardDTOService;
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
public class CreditCardDTOServiceImpl implements ICreditCardDTOService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardDTOServiceImpl.class);

    @Autowired
    private WebClient client;


    @Override
    public Mono<CreditCardDTO> updateCredit(CreditCardDTO credit) {
        LOGGER.info("initializing Credit Card Update");

        return client.put()
                .uri("/{id}", Collections.singletonMap("id",credit.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(credit)
                .retrieve()
                .bodyToMono(CreditCardDTO.class);
    }


    @Override
    public Mono<CreditCardDTO> findByPan(String pan) {
        Map<String, Object> params = new HashMap<String,Object>();
        LOGGER.info("initializing Credit query: " + pan);
        params.put("pan",pan);
        return client.get()
                .uri("/payment/{pan}",params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(CreditCardDTO.class))
                .doOnNext(c -> LOGGER.info("CreditCard Response: CreditCard Amounth={}", c.getTotalConsumption()));
    }
}
