package com.bootcamp.msCredit.handler;

import com.bootcamp.msCredit.models.dto.CustomerDTO;
import com.bootcamp.msCredit.models.entities.Credit;
import com.bootcamp.msCredit.services.ICreditService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Slf4j(topic = "credit_handler")
@Component
public class CreditHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditHandler.class);

    @Autowired
    private ICreditService service;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), Credit.class);
    }

    public Mono<ServerResponse> findCredit(ServerRequest request) {
        String id = request.pathVariable("id");

        return service.findById(id).flatMap(c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }


    public Mono<ServerResponse> newCredit(ServerRequest request){

        Mono<Credit> creditMono = request.bodyToMono(Credit.class);

        return creditMono.flatMap( credito -> service.getCustomer(credito.getCustomerIdentityNumber())
       .flatMap(customerDTO -> {
            credito.setCustomer(customerDTO);

            return service.create(credito);
                }))
                .flatMap( c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findAllByCustomerIdentityNumber(ServerRequest request){
        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");
        return  ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAllByCustomerIdentityNumber(customerIdentityNumber), Credit.class);
    }

    public Mono<ServerResponse> deleteCredit(ServerRequest request){

        String id = request.pathVariable("id");

        Mono<Credit> creditMono = service.findById(id);

        return creditMono
                .doOnNext(c -> LOGGER.info("deleteConsumption: consumptionId={}", c.getId()))
                .flatMap(c -> service.delete(c).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateCredit(ServerRequest request){
        Mono<Credit> creditMono = request.bodyToMono(Credit.class);
        String id = request.pathVariable("id");

        return service.findById(id).zipWith(creditMono, (db,req) -> {
                    db.setAmount(req.getAmount());
                    return db;
                }).flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.update(c),Credit.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
