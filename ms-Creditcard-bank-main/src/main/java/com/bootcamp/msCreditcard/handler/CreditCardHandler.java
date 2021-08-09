package com.bootcamp.msCreditcard.handler;

import com.bootcamp.msCreditcard.models.entities.CreditCard;
import com.bootcamp.msCreditcard.services.ICreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j(topic = "creditcard_handler")
@Component
public class CreditCardHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardHandler.class);

    @Autowired
    private ICreditCardService service;

    public Mono<ServerResponse> findAll(ServerRequest request){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.findAll(), CreditCard.class);
    }

    public Mono<ServerResponse> findCreditCard(ServerRequest request) {
        String id = request.pathVariable("id");
        return service.findById(id).flatMap(c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findCreditCardByPan(ServerRequest request) {
        String pan = request.pathVariable("pan");
        return service.findByPan(pan).flatMap(c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> newCreditCard(ServerRequest request){

        Mono<CreditCard> creditCardMono = request.bodyToMono(CreditCard.class);
        String customerIdentityNumber = request.pathVariable("customerIdentityNumber");

        return creditCardMono.flatMap( creditCard -> service.getCustomer(customerIdentityNumber)
                .flatMap(customerDTO -> {
                    creditCard.setCustomer(customerDTO);
                    String creditCardType = customerDTO.getCustomerIdentityNumber().length()==8? "Personal":"Empresarial";
                    creditCard.setCreditCardType(creditCardType);
                    return service.create(creditCard);
                })).flatMap( c -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(c)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteCreditCard(ServerRequest request){

        String id = request.pathVariable("id");

        Mono<CreditCard> creditCardMono = service.findById(id);

        return creditCardMono
                .doOnNext(c -> LOGGER.info("deleteConsumption: consumptionId={}", c.getId()))
                .flatMap(c -> service.delete(c).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateCreditCard(ServerRequest request){
        Mono<CreditCard> creditCardMono = request.bodyToMono(CreditCard.class);
        String id = request.pathVariable("id");

        return service.findById(id).zipWith(creditCardMono, (db,req) -> {
                    db.setTotalConsumption(req.getTotalConsumption());
                    db.setBalanceAmount(req.getBalanceAmount());
                    return db;
                }).flatMap( c -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.create(c),CreditCard.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
