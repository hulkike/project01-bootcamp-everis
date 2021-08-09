package com.example.mspasives.services;

import com.example.mspasives.exception.webclient.ArgumentWebClientNotValid;
import com.example.mspasives.models.entities.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class TransactionService {
    private final WebClient.Builder webClientBuilder;

    Logger logger = LoggerFactory.getLogger(BillService.class);

    @Autowired
    public TransactionService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Transaction> createTransaction(Transaction transaction){
        return webClientBuilder.build().post()
                .uri("/create")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(transaction), Transaction.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    logTraceResponse(logger, response);
                    return Mono.error(new RuntimeException("THE BILL UPDATE FAILED"));
                })
                .bodyToMono(Transaction.class);
    }

    public Mono<Transaction> findByAccountNumber(String accountNumber) {
        return webClientBuilder.build().get().uri("/acc/{accountNumber}", Collections.singletonMap("accountNumber", accountNumber))
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    logTraceResponse(logger, response);
                    return Mono.error(new ArgumentWebClientNotValid(
                            String.format("THE ACCOUNT NUMBER DONT EXIST IN MICRO SERVICE BILL-> %s", accountNumber)
                    ));
                })
                .bodyToMono(Transaction.class);
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
