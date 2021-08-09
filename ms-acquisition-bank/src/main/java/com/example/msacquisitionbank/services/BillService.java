package com.example.msacquisitionbank.services;

import com.example.msacquisitionbank.models.entities.Bill;
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

@Service
public class BillService {
    private final WebClient.Builder webClientBuilder;

    Logger logger = LoggerFactory.getLogger(BillService.class);

    @Autowired
    public BillService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Bill> createBill(Bill bill){
        return webClientBuilder
                .baseUrl("http://SERVICE-BILL/bill")
                .build()
                .post()
                .uri("/save")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(bill), Bill.class)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    logTraceResponse(logger, response);
                    return Mono.error(new RuntimeException("THE BILL CREATED FAILED"));
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
