package com.example.mstransaction.services;

import com.example.mstransaction.exception.webclient.ArgumentWebClientNotValid;
import com.example.mstransaction.models.entities.Bill;
import com.example.mstransaction.utils.CustomMessage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@Slf4j(topic = "BILL_WEBCLIENT_SERVICE")
public class BillService {
    private final WebClient.Builder webClientBuilder;
    private final CustomMessage customMessage;

    @Autowired
    public BillService(WebClient.Builder webClientBuilder, CustomMessage customMessage) {
        this.webClientBuilder = webClientBuilder;
        this.customMessage = customMessage;
    }

    public Mono<Bill> findByAccountNumber(String accountNumber) {
        return webClientBuilder
                .baseUrl("http://SERVICE-BILL/bill")
                .build()
                .get()
                .uri("/acc/{accountNumber}", Collections.singletonMap("accountNumber", accountNumber))
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    logTraceResponse(log, response);
                    return Mono.error(new ArgumentWebClientNotValid(
                            String.format("THE ACCOUNT NUMBER DONT EXIST IN MICRO SERVICE BILL-> %s", accountNumber)
                    ));
                })
                .bodyToMono(Bill.class);
    }

    public static void logTraceResponse(Logger log, ClientResponse response) {
        if (log.isTraceEnabled()) {
            log.trace("Response status: {}", response.statusCode());
            log.trace("Response headers: {}", response.headers().asHttpHeaders());
            response.bodyToMono(String.class)
                    .publishOn(Schedulers.boundedElastic())
                    .subscribe(body -> log.trace("Response body: {}", body));
        }
    }
}
