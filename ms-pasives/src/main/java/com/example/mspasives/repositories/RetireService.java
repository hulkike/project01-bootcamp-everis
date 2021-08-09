package com.example.mspasives.repositories;

import com.example.mspasives.exception.webclient.ArgumentWebClientNotValid;
import com.example.mspasives.models.entities.Bill;
import com.example.mspasives.models.entities.Retire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

public class RetireService {

    private final WebClient.Builder webClientBuilder;

    Logger logger = LoggerFactory.getLogger(RetireService.class);

    @Autowired
    public RetireService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
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
