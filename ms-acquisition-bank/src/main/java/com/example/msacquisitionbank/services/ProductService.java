package com.example.msacquisitionbank.services;

import com.example.msacquisitionbank.exception.ArgumentWebClientNotValid;
import com.example.msacquisitionbank.models.entities.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ProductService {
    private final WebClient.Builder webClientBuilder;

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Product> findByProductName(String productName) {
        return webClientBuilder
                .baseUrl("http://SERVICE-PRODUCT/product")
                .build()
                .get()
                .uri("/name/{productName}", Collections.singletonMap("productName", productName))
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    logTraceResponse(logger, response);
                    return Mono.error(new ArgumentWebClientNotValid(
                            String.format("THE PRODUCT NAME DONT EXIST IN MICRO SERVICE PRODUCT-> %s", productName)
                    ));
                })
                .bodyToMono(Product.class);
    }
    public Mono<Product> findByProductId(String productId) {
        return webClientBuilder
                .baseUrl("http://SERVICE-PRODUCT/product")
                .build()
                .get()
                .uri("/find/{productId}", Collections.singletonMap("productId", productId))
                .accept(APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatus::isError, response -> {
                    logTraceResponse(logger, response);
                    return Mono.error(new ArgumentWebClientNotValid(
                            String.format("THE PRODUCT WITH ID DOES NOT EXIST IN MICRO SERVICE PRODUCT-> %s", productId)
                    ));
                })
                .bodyToMono(Product.class);
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
